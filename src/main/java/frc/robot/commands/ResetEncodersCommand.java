// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.FloorIntake;
import frc.robot.subsystems.Shooter;

public class ResetEncodersCommand extends Command {
  private Drive m_drive = new Drive();
  private FloorIntake m_floorIntake = new FloorIntake();
  private Shooter m_shooter = new Shooter();
  private int timer = 0;

  public ResetEncodersCommand(Drive drive, FloorIntake floorIntake, Shooter shooter) {
    this.m_drive = drive;
    this.m_floorIntake = floorIntake;
    this.m_shooter = shooter;

    addRequirements(m_drive);
    addRequirements(m_floorIntake);
    addRequirements(m_shooter);
  }

  private boolean end = false;
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.resetEncoders();
    m_floorIntake.resetEncoders();
    m_shooter.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(timer<5){
      timer++;
      end = false;
    }
    else{
      end = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
