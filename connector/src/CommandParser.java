import sbw.architecture.datatype.*;
import sbw.project.cli.CommandLineInterface;
import sbw.project.cli.action.ActionCreational;
import sbw.project.cli.action.ActionSet;
import sbw.project.cli.action.ActionMiscellaneous;
import sbw.project.cli.action.command.misc.CommandDoExit;

public class CommandParser{

    public ActionSet actionSet;
    public String command;

    // create your parser
    // the ActionSet and command string are provided to you automatically.
    // do not do the parsing here
    public CommandParser(ActionSet actionSet, String command){

        this.actionSet = actionSet;
        this.actionCreational = actionSet.getActionCreational();
        this.command = command;

    }

    // do the parsing
    public void parse(){



    }

    // Input: CREATE AILERON <id> WITH LIMIT UP <angle1> DOWN <angle2> SPEED <speed> ACCELERATION <acceleration>
    // Action: creates an 'ActuatorAileron' with identifier 'id' that deflects 'angle1' degrees up (positive) from neutral (0 degrees) and 'angle2' degrees down (negative) at maximum speed 'speed' and acceleration 'acceleration'
    //         this calls doCreateAileron(), which creates and registers an instance of 'ActuatorAileron'
    public void createAileron(Identifier id, Angle angle1, Angle angle2, Speed speed, Acceleration acceleration){

        actionCreational.doCreateAileron(id, angle1, angle2, speed, acceleration);

    }

    // Input: CREATE ENGINE <id> WITH SPEED <speed> ACCELERATION <acceleration>
    // Action: creates an 'ActuatorEngine' with identifier 'id' with maximum speed 'speed' and acceleration 'acceleration'
    //         the interval is always [0..100] percent power
    //         this calls doCreateEngine(), which creates and registers an instance of 'ActuatorEngine'
    public void createEngine(Identifier id, Speed speed, Acceleration acceleration){



    }

    // Input: DECLARE AILERON CONTROLLER <id1> WITH AILERONS <idn>+ PRIMARY <idx> (SLAVE <idslave> TO <idmaster> BY <percent> PERCENT)*
    // Action: creates a 'ControllerAileron' with identifier 'id1' containing n ailerons 'idn,' where n is even
    //         the first half of n in order are on the left wing, and the second half on the right
    //         'idx' specifies which of 'idn' is the primary one that is directly commanded by a request to this controller
    //         it must be on the left wing
    //         the others respond based on it: all ailerons on the same side deflect in the same direction, and all on the opposite side deflect in the opposite direction (except when used as a speed brake; see III.3.b).
    //         'idslave' optionally bases its deflection (and likewise its opposite's) on 'idmaster' by percent 'percent'
    //         any chain of mixing is possible, but there are no cyclical relationships
    //         aileron configurations must be symmetrically identical
    //         this calls two variants of doDeclareAileronController(), which creates and registers an instance of 'ControllerAileron'
    public void declareAileronController(Identifier id1, Identifier idn, Identifier idx, Identifier idslave, Identifier idmaster, Percent percent){



    }

    // Input: DECLARE ENGINE CONTROLLER <id1> WITH ENGINE[S] <idn>+
    // Action: creates a 'ControllerEngine' with identifier 'id1' containing engines 'idn,' arrayed left to right in order, with identical configurations
    //         the plural form of ENGINE need not correspond grammatically to n
    //         this calls doDeclareEngineController(), which creates and registers an instance of 'ControllerEngine'
    public void declareEngineController(Identifier id1, Identifier idn){



    }

    // Input: DO <id> DEFLECT AILERONS <angle> UP|DOWN
    // Action: requests that aileron controller 'id' deflect its primary aileron respectively up or down to angle 'angle'
    //         the opposite ailerons will deflect in the other direction
    //         the slave ailerons will derive their angles as specified in the slave clause in II.3.a, if one was given; otherwise, the relationship is 100%.
    //         this calls submitCommand() with an instance of 'CommandDoDeflectAilerons'
    public void deflectAileron(Identifier id, Angle angle){



    }

    // Input: DO <id> SET POWER <power>
    // Action: requests that engine controller 'id' set the power of all engines to power 'power'
    //         this calls submitCommand() with an instance of 'CommandDoSetEnginePowerAll'
    public void enginePower(Identifier id, Power power){



    }

    // Input: @EXIT
    // Action: exits the system
    //         this calls submitCommand() with an instance of 'CommandDoExit'
    public void exit(){

        // Create a new CommandLineInterface to construct an ActionSet
        CommandLineInterface cli = new CommandLineInterface();
        // Create a new ActionSet to access the protected ActionMiscellaneous constructor
        ActionSet actions = new ActionSet(cli);
        // Create a new ActionMiscellaneous to access the protected submitCommand()
        ActionMiscellaneous action = actions.getActionMiscellaneous();
        // Create a new exitCommand() to direct the submitCommand()
        CommandDoExit exitCommand = new CommandDoExit();

        // Submit the exit command designed for a miscellaneous action
        action.submitCommand(exitCommand);


    }

}
