// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final static WPI_TalonSRX shooter1 = new WPI_TalonSRX(Constants.shooter1);
  private final static WPI_TalonSRX shooter2 = new WPI_TalonSRX(Constants.shooter2);
  private final static TalonFX shooter3 = new TalonFX(Constants.shooter3);



  public Shooter() {
    shooter3.setNeutralMode(NeutralModeValue.Coast);
    shooter2.setNeutralMode(NeutralMode.Coast);
    shooter1.setNeutralMode(NeutralMode.Coast);
  }

  //Main Commands
  public void shooterOn() {
    shooter1.set(1.0);
    shooter2.set(1.0);
    shooter3.set(1.0);
  }

  public void shooterIntakeOn(){
    shooter1.set(-0.5);
    shooter2.set(-0.5);
    shooter3.set(-0.5);
  }

  public  void shooterOff() {
    shooter1.set(0.0);
    shooter2.set(0.0);  
    shooter3.set(0.0);
  }

  public void ampShooterOn() {
    shooter1.set(0.35); //35
    shooter2.set(0.27); //20
    shooter3.set(0.1); //20
  }
  


  //PathPlanner Commands
  public Command shooterOnCommand(){
    return new InstantCommand(()->shooterOn());
  }

  public Command shooterOffCommand(){
    return new InstantCommand(()->shooterOff());
  }

  public void resetEncoders() {
    shooter1.setSelectedSensorPosition(0);
    shooter2.setSelectedSensorPosition(0);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
      // SmartDashboard.putNumber("Falcon Speed", shooter2.getVelocity().getValueAsDouble());
  }
}
  