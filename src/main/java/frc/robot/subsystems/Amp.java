// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Amp extends SubsystemBase {
    private CANSparkMax m_ampPlatform8 = new CANSparkMax(Constants.ampPlatform8, MotorType.kBrushed);
    // private RelativeEncoder m_encoder = m_ampPlatform8.getEncoder();




  /** Creates a new Amp. */
  public Amp() {
    m_ampPlatform8.setIdleMode(IdleMode.kBrake);
  }

  public void platformForwards() {
    m_ampPlatform8.set(0.2);
  }
  
  public void platformBackwards() {
    m_ampPlatform8.set(-0.23);
  }

  public void platformOff() {
    m_ampPlatform8.set(0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // SmartDashboard.putNumber("Amp Platform Encoder", m_ampPlatform8.getEncoder().getPosition());
  }
}
