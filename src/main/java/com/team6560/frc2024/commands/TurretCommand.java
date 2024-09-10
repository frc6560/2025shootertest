public class TurretCommand extends CommandBase {
    private Turret Turret;
    private ManualControls controls;
}

public void initialize() {
    Turret.setTurretPos(0.0);
}

public void execute() {
/*    if (controls.setTurretHome()) {
        Turret.setTurretPos(Constants.Turret.TURRET_HOME);
    } 
    else if (controls.setTurretFeedPos()) {
        Turret.setTurretPos(Constants.Turret.TURRET_FEED_POS);
    }
    */
}