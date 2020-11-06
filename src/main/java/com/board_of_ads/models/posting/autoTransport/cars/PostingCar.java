package com.board_of_ads.models.posting.autoTransport.cars;

import com.board_of_ads.models.posting.autoTransport.AutoTransport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posting_cars")
public class PostingCar extends AutoTransport {

    private String vinCode;
    private boolean isCarNew = false;
    private Long sellerId;

    @Size(max=120)
    private String typeOfUsedCarPosting;
    @Size(max=20)
    private String statePlateNumber;
    private int mileage;
    private byte numberOfOwners = 1;
    private int modelIdInAutoCatalogue;
    @Size(max=80)
    private String carColor;
    @Size(max=20)
    private String carBrand;
    @Size(max=50)
    private String carModel;
    private Short carYear;
    @Size(max=20)
    private String carBodyType;
    private byte numberOfDoors;
    private boolean wasInAccident;
    private boolean dealerServiced = true;
    private boolean underWarranty;
    private boolean hasServiceBook;
    @Size(max=50)
    private String powerSteeringType;
    @Size(max=50)
    private String climateControlType;
    private boolean onWheelControl;
    private boolean thermalGlass;
    @Size(max=50)
    private String interiorType;
    private boolean leatherWheel;
    private boolean sunroof = true;
    private boolean heatedFrontSeats;
    private boolean heatedRearSeats;
    private boolean heatedMirrors;
    private boolean heatedRearWindow;
    private boolean heatedWheel;
    @Size(max=50)
    private String powerWindowsType;
    private boolean powerFrontSeats;
    private boolean powerRearSeats;
    private boolean powerMirrorRegulation;
    private boolean powerSteeringWheelRegulation;
    private boolean powerMirrorClose;
    private boolean frontSeatsMemory;
    private boolean rearSeatsMemory;
    private boolean mirrorRegulationMemory;
    private boolean steeringWheelRegulationMemory;
    private boolean parkingAssist;
    private boolean rainSensor;
    private boolean lightSensor;
    private boolean rearParkingSensor;
    private boolean frontParkingSensor;
    private boolean blindSpotZoneControl;
    private boolean rearCamera;
    private boolean cruiseControl;
    private boolean onBoardComp;
    private boolean alarmSystem;
    private boolean powerDoorBlocking;
    private boolean immobilizer;
    private boolean satelliteAlarmControl;
    private boolean frontalAirbags;
    private boolean kneeAirbags;
    private boolean sideWindowAirbags;
    private boolean frontSideAirbags;
    private boolean rearSideAirbags;
    private boolean absSystem = true;
    private boolean dtcSystem;
    private boolean trackingControl;
    private boolean breakAssistSystem;
    private boolean emergencyBreakSystem;
    private boolean diffLockSystem;
    private boolean pedestrianDetectSystem;
    private boolean cdDvdBluRay;
    private boolean mp3;
    private boolean radio = true;
    private boolean tvSystem;
    private boolean videoSystem;
    private boolean mediaOnWheelControl;
    private boolean usb;
    private boolean aux;
    private boolean bluetooth;
    private boolean gpsNavigation;
    @Size(max=50)
    private String audioSystemType;
    private boolean subwoofer;
    @Size(max=50)
    private String frontLightType;
    private boolean antifogLights;
    private boolean frontLightCleaning;
    private boolean adaptiveLights;
    @Size(max=35)
    private String howToContactVsSeller;
    @Size(max=5)
    private String tyreSize;
    private boolean winterTyreSetIncluded;
    @Size(max=50)
    private String typeOfEngine;
    @Size(max=50)
    private String wheelDrive;
    @Size(max=50)
    private String transmission;
    @Size(max=50)
    private String modification;
    @Size(max=50)
    private String configuration;

}
