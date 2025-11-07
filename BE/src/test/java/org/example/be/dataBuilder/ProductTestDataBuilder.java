package org.example.be.dataBuilder;

import org.example.be.entity.Battery;
import org.example.be.entity.Member;
import org.example.be.entity.Product;
import org.example.be.entity.Vehicle;

import java.time.LocalDateTime;

public class ProductTestDataBuilder {
    public static Product createVehicleProduct(Member owner) {
        Product product = new Product();
        product.setProductsId(1);
        product.setName("Tesla Model 3");
        product.setProductType("VEHICLE");
        product.setDescription("Electric vehicle");
        product.setStatus("AVAILABLE");
        product.setCreatedAt(LocalDateTime.now());
        product.setMember(owner);

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Tesla");
        vehicle.setModel("Model 3");
        vehicle.setMileage(15000);
        vehicle.setRegisterYear(String.valueOf(2022));
        vehicle.setOrigin("USA");
        vehicle.setBatteryCapacity("75 kWh");
        vehicle.setCondition("EXCELLENT");
        vehicle.setName("Tesla Model 3 2022");

        product.setVehicle(vehicle);
        return product;
    }

    public static Product createBatteryProduct(Member owner) {
        Product product = new Product();
        product.setProductsId(2);
        product.setName("Samsung Battery 48V");
        product.setProductType("BATTERY");
        product.setDescription("High capacity battery");
        product.setStatus("AVAILABLE");
        product.setCreatedAt(LocalDateTime.now());
        product.setMember(owner);

        Battery battery = new Battery();
        battery.setName("Samsung 48V Battery");
        battery.setBrand("Samsung");
        battery.setCondition("GOOD");
        battery.setCapacityAh(100);
        battery.setVoltageV("48 V");
        battery.setYearAt("2023");
        battery.setOrigin("Korea");

        product.setBattery(battery);
        return product;
    }
}
