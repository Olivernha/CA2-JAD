package jad.jadca2.controller;

import jad.jadca2.dao.SlotDAO;
import jad.jadca2.model.Slot;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SlotController {

    @GetMapping("/getSlots")
    public List<Slot> getAvailableSlots() {
        try {
            SlotDAO dao = new SlotDAO();
            return dao.getAllSlots();
        } catch (Exception e) {
            System.out.println("Error fetching slots: " + e.getMessage());
            return null;
        }
    }
}
