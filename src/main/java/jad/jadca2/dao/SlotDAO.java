package jad.jadca2.dao;

import jad.jadca2.dbaccess.DBConnection;
import jad.jadca2.model.Slot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SlotDAO {
    private Connection connection;

    public SlotDAO() {
        this.connection = DBConnection.getConnection();
    }

    public List<Slot> getAllSlots() {
        List<Slot> slots = new ArrayList<>();
        String query = "SELECT * FROM slots ORDER BY slot_start_time";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Slot slot = new Slot();
                slot.setSlotId(rs.getInt("slot_id"));
                slot.setSlotStartTime(rs.getString("slot_start_time"));
                slot.setSlotEndTime(rs.getString("slot_end_time"));
                slots.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slots;
    }
}
