// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRob
import edu.wpi.first.math.geometry.Rotation2d;ot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  public Command autonomousCommand;
  public RobotContainer RobotContainer;
  public Timer m_timer;

  public Robot() {

  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    
    RobotContainer = new RobotContainer();
    m_timer = new Timer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    autonomousCommand = RobotContainer.getAutonomousCommand();

    if(autonomousCommand != null) {
      autonomousCommand.schedule();
    }
    // m_timer.reset();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
//ONE PIECE
    // if(m_timer.get() > 0.5) { //Charges up
    //   Shooter.shooterOn();    
    // }

    // if(m_timer.get() > 1.5) { //Shoots
    //   FloorIntake.floorIntakeFORWARD();
    // }

    // if(m_timer.get() > 4){
    //   Drive.m_robotDrive.curvatureDrive(0.3, 0,true);
    // }

    // if(m_timer.get() > 10){
    //   Drive.m_robotDrive.curvatureDrive(0.0, 0,true);
    //   Shooter.shooterOff();    
    // }

//TWO PIECE
    // if(m_timer.get() > 0.5) { //Charges up
    //   Shooter.shooterOn();    
    // }

    // if(m_timer.get() > 1.5) { //Shoots
    //   FloorIntake.floorIntakeFORWARD();
    // }

    // if()

    // if(m_timer.get() > 4){
    //   Drive.m_robotDrive.curvatureDrive(0.3, 0,true);
    // }

    // if(m_timer.get() > 10){
    //   Drive.m_robotDrive.curvatureDrive(0.0, 0,true);
    //   Shooter.shooterOff();    
    // }



  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    if(autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // SmartDashboard.putNumber("Sensor", GlobalVariables.getSensorVal());

  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
  
}
