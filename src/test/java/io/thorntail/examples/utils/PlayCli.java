package io.thorntail.examples.utils;


import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.scriptsupport.CLI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class PlayCli implements ServerSetupTask {

    protected List<String> cliCommands = new ArrayList<>();

    protected void initWildfly(ManagementClient managementClient) {
        final String host = managementClient.getWebUri().getHost();
        System.out.println("Adding cli commands");
        processCli(host, cliCommands, true);
    }


    @Override
    public void tearDown(ManagementClient managementClient, String s) throws Exception {
        final String host = managementClient.getWebUri().getHost();
        System.out.println("Removing cli commands");
        final List<String> removeCli = cliCommands.stream()
                .map(cli -> replaceCliOperation(cli, "remove"))
                .collect(Collectors.toList());
        processCli(host, removeCli, false);
    }


    private void processCli(String host, final List<String> cliCommands, boolean adding) {
        CLI cli = CLI.newInstance();
        try {
            cli.connect(host, 9990, null, null);
            CommandContext commandContext = cli.getCommandContext();

            final Predicate<String> stringPredicate = cliCommand -> {
                String cliPresence = replaceCliOperation(cliCommand, "read-resource");
                System.out.println(cliPresence);
                commandContext.handleSafe(cliPresence);
                int desireResult = adding ? 1 : 0;
                return desireResult == commandContext.getExitCode();
            };

            cliCommands.stream()
                    .map(String::trim)
                    .filter(stringPredicate)
                    .peek(System.out::println)
                    .forEach(commandContext::handleSafe);

           // commandContext.handleSafe(":reload");

        } finally {
            cli.disconnect();
        }
    }

    private String replaceCliOperation(final String cli, final String newCliOperation) {
        String cliWithoutEndOperation = cli.substring(0, cli.lastIndexOf(":"));
        return String.format("%s:%s", cliWithoutEndOperation, newCliOperation);
    }
}
