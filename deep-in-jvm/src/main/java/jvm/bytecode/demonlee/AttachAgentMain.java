package jvm.bytecode.demonlee;

import com.sun.tools.attach.VirtualMachine;

/**
 * @author Demon.Lee
 * @date 2023-12-18 10:58
 */
public class AttachAgentMain {

    public static void main(String[] args) throws Exception {
        if (args.length <= 1) {
            System.out.println("Usage: java AttachAgentMain <PID> /PATH/TO/AGENT.jar");
            return;
        }

        for (String arg : args) {
            System.out.println("arg: " + arg);
        }

        VirtualMachine vm = VirtualMachine.attach(args[0]);
        vm.loadAgent(args[1], args[2]);
    }
}
