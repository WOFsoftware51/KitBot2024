package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class GlobalVariables {
    public static double dSpeed = 0.6;
    public static boolean isShooting = false;

    private static DigitalInput m_sensor = new DigitalInput(0);

    public static int getSensorVal(){
        if(m_sensor.get()) {
            return 1;
        }
        else{
            return -1;
        }
    }
}
