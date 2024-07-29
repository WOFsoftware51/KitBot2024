package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class Constants {
    public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(20.875));

    public static final int rightDrive = 2;
    public static final int rightDrive2 = 4;
    public static final int leftDrive = 7;
    public static final int leftDrive2 = 3;
    public static final int shooter1 = 5;
    public static final int shooter2 = 6;
    public static final int shooter3 = 9;
    public static final int ampPlatform8 = 8;
    public static final int floorIntake1 = 1;


    public static final float maxSpeed = 4.5f;

    public static final float normalDspeed = 0.6f;
    public static final float rotationSpeed = 0.45f; 
}