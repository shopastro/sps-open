package com.shopastro.sps.open.statemachine.impl;

import com.shopastro.sps.open.statemachine.State;
import com.shopastro.sps.open.statemachine.StateMachine;
import com.shopastro.sps.open.statemachine.Transition;
import com.shopastro.sps.open.statemachine.Visitor;

/**
 * PlantUMLVisitor
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-09 7:47 PM
 */
public class PlantUMLVisitor implements Visitor {
    StateMachine<?, ?, ?> visitable;

    /**
     * Since the state machine is stateless, there is no initial state.
     * <p>
     * You have to add "[*] -> initialState" to mark it as a state machine diagram.
     * otherwise it will be recognized as a sequence diagram.
     *
     * @param visitable the element to be visited.
     * @return
     */
    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> visitable) {
        this.visitable = visitable;
        StringBuilder sb = new StringBuilder();

        sb.append("https://www.plantuml.com/plantuml/uml" + LF + "@startuml" + LF);

        if (visitable.getInitStates() != null) {
            for (Object row : visitable.getInitStates()) {
                sb.append("[*] --> " + row + LF);
            }
        }


        return sb.toString();
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> visitable) {
        StringBuilder sb = new StringBuilder();
        if (visitable.getTerminalStates() != null) {
            for (Object row : visitable.getTerminalStates()) {
                sb.append(row + " --> [*]" + LF);
            }
        }

        return sb.append("@enduml").toString();
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        for (Transition transition : state.getAllTransitions()) {

            boolean auto = transition.isAuto();
            sb.append(transition.getSource().getId())
                    .append(" --> ")
                    .append(transition.getTarget().getId())
                    .append(" : ")
                    .append(auto ? "<AUTO>" : "")
                    .append(transition.getEvent())
                    .append((":"))
                    .append(transition.getCondition() == null ? "NA" : transition.getCondition().name())
                    .append((":"))
                    .append(transition.getAction() == null ? "NA" : transition.getAction().getClass().getSimpleName())
                    .append(LF);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> state) {
        return "";
    }
}
