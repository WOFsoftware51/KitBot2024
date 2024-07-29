
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.GlobalVariables;
import frc.robot.commands.Auton_Wait;

public class Drive extends SubsystemBase {
  // The motors on the left side of the drive.
  private final static WPI_TalonSRX m_leftDrive = new WPI_TalonSRX(Constants.leftDrive);
  private final static WPI_TalonSRX m_leftDrive2 = new WPI_TalonSRX(Constants.leftDrive2);

  // The motors on the right side of the drive.
  private final static WPI_TalonSRX m_rightDrive = new WPI_TalonSRX(Constants.rightDrive);
  private final static WPI_TalonSRX m_rightDrive2 = new WPI_TalonSRX(Constants.rightDrive2);

  public static DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDrive, m_rightDrive);




  public void drive(double leftY, double rightX){
    double getLeftY = Math.pow(leftY,3)*GlobalVariables.dSpeed;
    double getRightX = Math.pow(rightX,3)*Constants.rotationSpeed;
    m_robotDrive.curvatureDrive(getLeftY, getRightX,true);
    m_robotDrive.feed();
  }

    public void driveAuton(double speed){
    m_leftDrive.set(speed);
    m_rightDrive.set(speed);
  }


  public void resetYaw() {
    m_gyro.reset();
  }

  
  //Brake
  public void brake(){
    m_leftDrive2.setNeutralMode(NeutralMode.Brake);
    m_rightDrive2.setNeutralMode(NeutralMode.Brake);
    m_leftDrive.setNeutralMode(NeutralMode.Brake);
    m_rightDrive.setNeutralMode(NeutralMode.Brake);
  }
  

  //Normal
  public void coastMode() {
    m_leftDrive2.setNeutralMode(NeutralMode.Coast);
    m_rightDrive2.setNeutralMode(NeutralMode.Coast);
    m_leftDrive.setNeutralMode(NeutralMode.Coast);
    m_rightDrive.setNeutralMode(NeutralMode.Coast);
  }

  //Commands
  public Command brakeCommand(){
    return new InstantCommand(() -> brake());
  }

  public Command driveCommand(double speed, int time){
    return new ParallelRaceGroup(
      new RunCommand(() -> driveAuton(speed)),
      new Auton_Wait(time)
    );
  }
  
  // The gyro sensor
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP); 


  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  
  /** Creates a new DriveSubsystem. */
  public Drive() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_leftDrive.setSafetyEnabled(true);
    m_rightDrive.setSafetyEnabled(true);
    GlobalVariables.dSpeed = 0.6f;
    m_leftDrive2.follow((m_leftDrive));
    m_rightDrive2.follow(m_rightDrive);
    m_rightDrive.setInverted(true);
    m_leftDrive2.setInverted(false);
    m_rightDrive2.setInverted(true);
    m_leftDrive.setNeutralMode(NeutralMode.Coast);
    m_rightDrive.setNeutralMode(NeutralMode.Coast);

    resetEncoders();
    m_odometry =
        new DifferentialDriveOdometry(
            m_gyro.getRotation2d(), m_leftDrive.getSelectedSensorPosition(), m_rightDrive.getSelectedSensorPosition());


          
   // All other subsystem initialization
    // ...

    // Configure AutoBuilder last
    // AutoBuilder.configureRamsete(
    //         this::getPose, // Robot pose supplier
    //         this::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
    //         this::getCurrentSpeeds, // Current ChassisSpeeds supplier
    //         this::driveRelative, // Method that will drive the robot given ChassisSpeeds
    //         new ReplanningConfig(), // Default path replanning config. See the API for the options here
    //         () -> false,
    //         this // Reference to this subsystem to set requirements
    // );
  }

  public ChassisSpeeds getCurrentSpeeds(){
    return Constants.kinematics.toChassisSpeeds(getWheelSpeeds());
  }

  public void driveRelative(ChassisSpeeds dSup) {
    double getLeftY = Math.pow(dSup.vxMetersPerSecond/Constants.maxSpeed,3)*GlobalVariables.dSpeed;
    double getRightX = Math.pow(dSup.omegaRadiansPerSecond/Constants.maxSpeed,3)*Constants.rotationSpeed;

    m_robotDrive.curvatureDrive(getLeftY, getRightX,true);
    m_robotDrive.feed();
  }
  

  
  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftDrive.getSelectedSensorVelocity(), m_rightDrive.getSelectedSensorVelocity());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(
        m_gyro.getRotation2d(), m_leftDrive.getSelectedSensorPosition(), m_rightDrive.getSelectedSensorPosition(), pose);
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_robotDrive.arcadeDrive(fwd, rot);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */


  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_leftDrive.setSelectedSensorPosition(0);
    m_leftDrive2.setSelectedSensorPosition(0);
    m_rightDrive.setSelectedSensorPosition(0);
    m_rightDrive2.setSelectedSensorPosition(0);
    m_robotDrive.feed();
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftDrive.getSelectedSensorPosition() + m_rightDrive.getSelectedSensorPosition()) / 2.0;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_robotDrive.setMaxOutput(maxOutput);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(m_gyro.getRotation2d(), m_leftDrive.getSelectedSensorPosition(), m_rightDrive.getSelectedSensorPosition());
    
    SmartDashboard.putNumber("Yaw", m_gyro.getRotation2d().getDegrees());

    SmartDashboard.putNumber("LeftDrive1", m_leftDrive.getSelectedSensorPosition());
    SmartDashboard.putNumber("RightDrive1", m_rightDrive.getSelectedSensorPosition());
    SmartDashboard.putNumber("Pose X", getPose().getX());
    SmartDashboard.putNumber("Pose Y", getPose().getY());
    SmartDashboard.putNumber("Pose Rotation", getPose().getRotation().getDegrees());

  }
}