package com.antigravity.prathamchikitse

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.antigravity.prathamchikitse.databinding.ActivityHospitalFinderBinding
import com.antigravity.prathamchikitse.models.Hospital
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class HospitalFinderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalFinderBinding
    private lateinit var fusedLocation: FusedLocationProviderClient
    private lateinit var adapter: HospitalAdapter

    companion object {
        private const val LOCATION_PERMISSION_CODE = 101
        private const val SEARCH_RADIUS_METERS = 5000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalFinderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        adapter = HospitalAdapter(emptyList()) { hospital ->
            // Open in maps
            val uri = Uri.parse("geo:${hospital.lat},${hospital.lon}?q=${Uri.encode(hospital.name)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // fallback: Google Maps URL
                startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=${hospital.lat},${hospital.lon}")))
            }
        }

        binding.rvHospitals.layoutManager = LinearLayoutManager(this)
        binding.rvHospitals.adapter = adapter

        binding.btnRetry.setOnClickListener { checkPermissionsAndLoad() }

        checkPermissionsAndLoad()
    }

    private fun checkPermissionsAndLoad() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fetchLocationAndHospitals()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CODE &&
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocationAndHospitals()
        } else {
            showError("Location permission denied. Cannot find nearby hospitals.")
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocationAndHospitals() {
        showLoading(true)
        fusedLocation.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    binding.tvStatus.text = "📍 Location: ${String.format("%.4f", location.latitude)}, ${String.format("%.4f", location.longitude)}"
                    binding.tvStatus.visibility = View.VISIBLE
                    fetchNearbyHospitals(location.latitude, location.longitude)
                } else {
                    showError("Could not get location. Make sure GPS is enabled.")
                }
            }
            .addOnFailureListener {
                showError("Location error: ${it.message}")
            }
    }

    private fun fetchNearbyHospitals(lat: Double, lon: Double) {
        lifecycleScope.launch {
            try {
                val hospitals = withContext(Dispatchers.IO) {
                    queryOverpassAPI(lat, lon)
                }
                if (hospitals.isEmpty()) {
                    showError("No hospitals found within ${SEARCH_RADIUS_METERS / 1000} km.")
                } else {
                    showLoading(false)
                    binding.tvCount.text = "Found ${hospitals.size} hospitals nearby"
                    adapter.updateData(hospitals)
                }
            } catch (e: Exception) {
                showError("Network error: ${e.message}")
            }
        }
    }

    private fun queryOverpassAPI(lat: Double, lon: Double): List<Hospital> {
        val query = """
            [out:json][timeout:25];
            (
              node["amenity"="hospital"](around:$SEARCH_RADIUS_METERS,$lat,$lon);
              way["amenity"="hospital"](around:$SEARCH_RADIUS_METERS,$lat,$lon);
              node["amenity"="clinic"](around:$SEARCH_RADIUS_METERS,$lat,$lon);
              node["healthcare"="hospital"](around:$SEARCH_RADIUS_METERS,$lat,$lon);
            );
            out center;
        """.trimIndent()

        val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
        val url = "https://overpass-api.de/api/interpreter?data=$encodedQuery"

        val response = URL(url).readText()
        val json = JSONObject(response)
        val elements = json.getJSONArray("elements")

        val hospitals = mutableListOf<Hospital>()
        val origin = Location("").apply { latitude = lat; longitude = lon }

        for (i in 0 until elements.length()) {
            val el = elements.getJSONObject(i)
            val tags = el.optJSONObject("tags") ?: continue

            val name = tags.optString("name", "").takeIf { it.isNotBlank() }
                ?: tags.optString("name:en", "").takeIf { it.isNotBlank() }
                ?: "Hospital / Clinic"

            // Get lat/lon: for nodes it's direct, for ways it's in "center"
            val elLat: Double
            val elLon: Double
            if (el.has("lat")) {
                elLat = el.getDouble("lat")
                elLon = el.getDouble("lon")
            } else if (el.has("center")) {
                val center = el.getJSONObject("center")
                elLat = center.getDouble("lat")
                elLon = center.getDouble("lon")
            } else continue

            val address = buildAddress(tags)
            val phone = tags.optString("phone", tags.optString("contact:phone", ""))

            val dest = Location("").apply { latitude = elLat; longitude = elLon }
            val distance = origin.distanceTo(dest)

            hospitals.add(Hospital(name, elLat, elLon, address, phone, distance))
        }

        return hospitals.sortedBy { it.distanceMeters }
    }

    private fun buildAddress(tags: JSONObject): String {
        val parts = listOfNotNull(
            tags.optString("addr:housenumber", "").takeIf { it.isNotBlank() },
            tags.optString("addr:street", "").takeIf { it.isNotBlank() },
            tags.optString("addr:suburb", "").takeIf { it.isNotBlank() },
            tags.optString("addr:city", "").takeIf { it.isNotBlank() }
        )
        return parts.joinToString(", ")
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvLoading.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvError.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE
        if (!show) binding.rvHospitals.visibility = View.VISIBLE
    }

    private fun showError(msg: String) {
        binding.progressBar.visibility = View.GONE
        binding.tvLoading.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = msg
        binding.btnRetry.visibility = View.VISIBLE
        binding.rvHospitals.visibility = View.GONE
    }
}
