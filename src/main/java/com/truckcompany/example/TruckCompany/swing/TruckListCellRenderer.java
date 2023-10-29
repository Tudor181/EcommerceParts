package com.truckcompany.example.TruckCompany.swing;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.truckcompany.example.TruckCompany.Domain.Truck;

public class TruckListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        if (value instanceof Truck) {
            Truck truck = (Truck) value;
            String textToDisplay = "Manufacturer: " + truck.getManufacturer() + " | Registration: "
                    + truck.getNrOfRegistration();
            return super.getListCellRendererComponent(list, textToDisplay, index, isSelected, cellHasFocus);
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

}
