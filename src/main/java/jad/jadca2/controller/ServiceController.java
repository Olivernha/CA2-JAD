package jad.jadca2.controller;

import jad.jadca2.dao.ServiceDAO;
import jad.jadca2.model.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private final ServiceDAO serviceDAO = new ServiceDAO();

    // CREATE - Post new service
    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody Service service) {
        try {
            boolean created = serviceDAO.addService(service);
            if (created) {
                return ResponseEntity.ok(service);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // READ - Get all services
    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        try {
            List<Service> services = serviceDAO.getAllServices();
            if (services.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // READ - Get service by ID
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable("id") int id) {
        try {
            Service service = serviceDAO.getServiceById(id);
            if (service != null) {
                return ResponseEntity.ok(service);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // UPDATE - Update service by ID
    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable("id") int id, @RequestBody Service service) {
        try {
            service.setId(id);
            boolean updated = serviceDAO.updateService(service);
            if (updated) {
                return ResponseEntity.ok(service);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // DELETE - Delete service by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") int id) {
        try {
            boolean deleted = serviceDAO.deleteService(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET - Fetch service statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getServiceStatistics() {
        try {
            Map<String, Object> stats = serviceDAO.getServiceStatistics();
            if (stats.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET - Fetch price range distribution
    @GetMapping("/price-distribution")
    public ResponseEntity<Map<String, Integer>> getPriceRangeDistribution() {
        try {
            Map<String, Integer> priceRanges = serviceDAO.getPriceRangeDistribution();
            if (priceRanges.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(priceRanges);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET - Fetch best-rated services
    @GetMapping("/best-rated")
    public ResponseEntity<List<Service>> getBestRatedServices() {
        try {
            List<Service> bestRatedServices = serviceDAO.getServicesByRating("DESC");
            if (bestRatedServices.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(bestRatedServices);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET - Fetch lowest-rated services
    @GetMapping("/lowest-rated")
    public ResponseEntity<List<Service>> getLowestRatedServices() {
        try {
            List<Service> lowestRatedServices = serviceDAO.getServicesByRating("ASC");
            if (lowestRatedServices.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lowestRatedServices);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET - Fetch high-demand/low-availability services
    @GetMapping("/high-demand")
    public ResponseEntity<List<Service>> getHighDemandServices() {
        try {
            List<Service> highDemandServices = serviceDAO.getServicesByAvailabilityAndDemand();
            if (highDemandServices.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(highDemandServices);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET - Search services by various criteria
    @GetMapping("/search")
    public ResponseEntity<List<Service>> searchServices(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        try {
            List<Service> services = serviceDAO.searchServices(keyword, categoryId, minPrice, maxPrice);
            if (services.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET - Fetch services by category id
    @GetMapping("/get-services-by-categoryId")
    public ResponseEntity<List<Service>> getServicesByCategoryId(@RequestParam("id") String categoryId) {
        try {
            List<Service> services = serviceDAO.getServicesByCategoryId(categoryId);
            if (services.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // GET - Fetch services by category name
    @GetMapping("/get-services-by-categoryName")
    public ResponseEntity<List<Service>> getServicesByCategoryName(@RequestParam("name") String categoryName) {
        try {
            List<Service> services = serviceDAO.getServicesByCategoryName(categoryName);
            if (services.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}