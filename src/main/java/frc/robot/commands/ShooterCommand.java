// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.GlobalVariables;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command {
  private Shooter m_shooter;
  /** Creates a new ShooterCommand. */

  public ShooterCommand(Shooter shoot) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_shooter = shoot;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.shooterOn();
    GlobalVariables.isShooting = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.shooterOff();
    GlobalVariables.isShooting = false;

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
