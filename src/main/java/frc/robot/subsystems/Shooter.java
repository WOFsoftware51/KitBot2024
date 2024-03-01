// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private final static WPI_TalonSRX shooter1 = new WPI_TalonSRX(Constants.Mod5);
  private final static WPI_TalonSRX shooter2 = new WPI_TalonSRX(Constants.Mod1);


  public Shooter() {}

  //Main Commands
  public void shooterOn() {
    shooter1.set(1.0);
    shooter2.set(1.0);
  }

  public void shooterIntakeOn(){
    shooter1.set(-0.5);
    shooter2.set(-0.5);
  }

  public void shooterOff() {
    shooter1.set(0.0);
    shooter2.set(0.0);
  }

  public void ampShooterOn() {
    shooter1.set(0.40); //40
    shooter2.set(-0.15); //27 

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
  }
}
