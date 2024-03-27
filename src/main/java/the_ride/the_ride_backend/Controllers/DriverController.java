package the_ride.the_ride_backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Services.DriverService;

import java.util.List;

@RestController
public class DriverController {
    private final DriverService _driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        _driverService = driverService;
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getDriversWithinBoundingBoxCoordinates(@Param("minLat") double minLat,
                                                                               @Param("maxLat") double maxLat,
                                                                               @Param("minLon") double minLon,
                                                                               @Param("maxLon") double maxLon) {
        List<Driver> articles = this._driverService.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        return new ResponseEntity<>(articles, headers, HttpStatus.OK);
    }
}
