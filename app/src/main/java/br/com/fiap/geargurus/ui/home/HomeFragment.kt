package br.com.fiap.geargurus.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.geargurus.R
import br.com.fiap.geargurus.databinding.FragmentHomeBinding
import br.com.fiap.geargurus.ui.RatingActivity
import br.com.fiap.geargurus.ui.UnlockActivity
import com.tomtom.quantity.Distance
import com.tomtom.sdk.common.measures.FormattedDistance
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProviderConfig
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.common.WidthByZoom
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.route.RouteOptions
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.map.display.ui.MapReadyCallback
import com.tomtom.sdk.routing.RoutePlanningCallback
import com.tomtom.sdk.routing.RoutePlanningResponse
import com.tomtom.sdk.routing.RoutingFailure
import com.tomtom.sdk.routing.online.OnlineRoutePlanner
import com.tomtom.sdk.routing.options.Itinerary
import com.tomtom.sdk.routing.options.RoutePlanningOptions
import com.tomtom.sdk.routing.route.Route
import com.tomtom.sdk.search.SearchCallback
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.SearchResponse
import com.tomtom.sdk.search.common.error.SearchFailure
import com.tomtom.sdk.search.online.OnlineSearch
import com.tomtom.sdk.search.ui.SearchResultClickListener
import com.tomtom.sdk.search.ui.SearchResultsView
import com.tomtom.sdk.search.ui.SearchView
import com.tomtom.sdk.search.ui.SearchViewListener
import com.tomtom.sdk.search.ui.model.PlaceDetails
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds


class HomeFragment : Fragment(), MapReadyCallback{

    private lateinit var tomTomMap: TomTomMap;

    private var _binding: FragmentHomeBinding? = null

    private var locationProvider : LocationProvider? = null
    private var _userLocation : GeoPoint? = null

    private var _step = 1
    private var _steps = listOf<LinearLayout>()

    private var _searchView : SearchView? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val i = activity?.intent

        val goUnlock : TextView = root.findViewById(R.id.go_unlock)

        goUnlock.setOnClickListener {
            val i = Intent(this@HomeFragment.context, UnlockActivity::class.java)
            startActivity(i)
        }

        val goGiveBack: TextView = root.findViewById(R.id.go_give_back)

        goGiveBack.setOnClickListener {
            giveBack(goUnlock, goGiveBack)
        }

        if (i != null) {
            checkUnlocked(i, goUnlock, goGiveBack)
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as? MapFragment

        val searchAPI = context?.let { OnlineSearch.create(it, "bvmLazIooaw7NVIDdxQUoKs7eVNAC9l4") }

        val searchView : SearchView? = root.findViewById(R.id.search_view) as? SearchView
        _searchView = searchView

        val searchResultsView : SearchResultsView? = root.findViewById(R.id.search_results_view) as? SearchResultsView

        val routePlanner = context?.let { OnlineRoutePlanner.create(it, "bvmLazIooaw7NVIDdxQUoKs7eVNAC9l4") }

        val step1 : LinearLayout = root.findViewById(R.id.step_1)

        val loadingView : LinearLayout = root.findViewById(R.id.loading)

        _steps = listOf(loadingView, step1)

        loadingView.visibility = View.VISIBLE

        searchResultsView?.searchResultClickListener = SearchResultClickListener { placeDetails: PlaceDetails ->
            searchView?.setQuery("")
            searchResultsView?.clear()
            tomTomMap.removeRoutes()

            step1.visibility = View.GONE

            _step = 0

            loadingView.visibility = View.VISIBLE

            val routePlanningOptions = _userLocation?.let {
                Itinerary(
                    it,
                    placeDetails.position
                )
            }?.let { RoutePlanningOptions(itinerary = it) }

            if (routePlanningOptions != null) {
                routePlanner?.planRoute(
                    routePlanningOptions,
                    object : RoutePlanningCallback {
                        override fun onSuccess(result: RoutePlanningResponse) {
                            Log.i("Route", result.toString())
                            val routeOptions = RouteOptions(
                                geometry = result.routes[0].legs[0].points,
                                color = Color.BLUE,
                                outlineWidth = 3.0,
                                widths = listOf(WidthByZoom(5.0)),
                                progress = Distance.meters(1000.0),
                                departureMarkerVisible = true,
                                destinationMarkerVisible = true
                            )
                            val route = tomTomMap.addRoute(routeOptions)

                            _step = 1

                            loadingView.visibility = View.GONE
                            step1.visibility = View.VISIBLE
                        }

                        override fun onFailure(failure: RoutingFailure) {
                            _step = 1
                            loadingView.visibility = View.GONE
                            _steps[1].visibility = View.VISIBLE
                            searchResultsView?.displayError("Erro ao calcular rota, tente novamente")
                        }

                        override fun onRoutePlanned(route: Route) {
                            //
                        }
                    })
            } else {
                _step = 1
                loadingView.visibility = View.GONE
                _steps[1].visibility = View.VISIBLE
                searchResultsView?.displayError("Erro ao calcular rota, tente novamente")
            }
        }

        searchView?.searchViewListener = object : SearchViewListener {
            override fun onSearchQueryCancel() {
                searchResultsView?.clear()
            }

            override fun onSearchQueryChanged(input: String) {
                if(searchView?.hasInput() == true){
                    val searchOptions = SearchOptions(query = input, limit = 5, locale = Locale("pt", "BR"))
                    searchAPI?.search(
                        searchOptions,
                        object : SearchCallback {
                            override fun onFailure(failure: SearchFailure) {
                                searchResultsView?.displayError("Erro ao encontrar informações no servidor")
                            }

                            override fun onSuccess(result: SearchResponse) {
                                val placeDetails : ArrayList<PlaceDetails> = arrayListOf()

                                result.results.forEach { searchResult ->
                                    val place : PlaceDetails? = searchResult.place.address?.let {
                                        PlaceDetails(searchResult.place.name, it.streetName, it.freeformAddress,
                                            it.localName, it.municipality, it.countryTertiarySubdivision, it.countrySecondarySubdivision,
                                            it.countrySubdivision, it.country, FormattedDistance("", ""), searchResult.place.coordinate,
                                            searchResult.place.entryPoints
                                        )
                                    }
                                    if (place != null) {
                                        placeDetails.add(place)
                                    }
                                }
                                searchResultsView?.update(placeDetails as List<PlaceDetails>)
                            }
                        }
                    )
                }else{
                    searchResultsView?.clear()
                }
            }

            override fun onCommandInsert(command: String) {
                //
            }
        }

        mapFragment?.getMapAsync { tomtomMap: TomTomMap ->
            onMapReady(tomtomMap)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: TomTomMap) {
        this.tomTomMap = map

        configLocationProvider()

        addLocationMarker()
    }

    private fun configLocationProvider(){
        val androidLocationProviderConfig = AndroidLocationProviderConfig(
            minTimeInterval = 150L.milliseconds,
            minDistance = Distance.meters(20.0)
        )

        locationProvider = activity?.applicationContext?.let {
            AndroidLocationProvider(
                context = it,
                config = androidLocationProviderConfig
            )
        }

        tomTomMap!!.setLocationProvider(locationProvider)

        if (activity?.applicationContext?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && activity?.applicationContext?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                && shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }else{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),1)
            }
        }else{
            Log.i("Maps", "Permissões garantidas")
            locationEnable()
        }
    }

    private fun locationEnable(){
        locationProvider?.enable()

        if (locationProvider != null) {
            Log.i("Maps", "Location Provider Not Null")
        }

        val onLocationUpdateListener = OnLocationUpdateListener { location: GeoLocation ->
                Log.i("Maps", location.toString())
                tomTomMap.moveCamera(CameraOptions(location.position, 15.0))
                _userLocation = location.position

                _steps[0].visibility = View.GONE
                _steps[_step].visibility = View.VISIBLE
            }
        locationProvider?.addOnLocationUpdateListener(onLocationUpdateListener)

        tomTomMap.setLocationProvider(locationProvider)
    }

    private fun addLocationMarker(){
        val locationMarker = LocationMarkerOptions(type = LocationMarkerOptions.Type.Chevron)
        tomTomMap.enableLocationMarker(locationMarker)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0] != -1 || grantResults[1] != -1){
            locationEnable()
        }else{
            Toast.makeText(activity?.applicationContext, "Você deve permitir o acesso a sua localização para usar o app", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()

        _searchView?.setQuery("")
    }

    private fun checkUnlocked(intent: Intent, goUnlock: TextView, goGiveBack: TextView){
        val showModal = intent.getStringExtra("ShowModal")

        if(showModal.equals("true")){
            goUnlock.visibility = View.GONE
            goGiveBack.visibility = View.VISIBLE

            Toast.makeText(activity, "Patinete/Bicicleta desbloqueado(a)", Toast.LENGTH_SHORT).show()
            intent.removeExtra("ShowModal")
        }
    }

    private fun giveBack(goUnlock: TextView, goGiveBack: TextView){
        goGiveBack.visibility = View.GONE
        goUnlock.visibility = View.VISIBLE

        val i = Intent(this@HomeFragment.context, RatingActivity::class.java)
        startActivity(i)

        Toast.makeText(activity, "Patinete/Bicicleta devolvido(a)", Toast.LENGTH_SHORT).show()
    }

}
