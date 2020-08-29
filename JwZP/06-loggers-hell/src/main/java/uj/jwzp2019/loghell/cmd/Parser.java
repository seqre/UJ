package uj.jwzp2019.loghell.cmd;

import org.apache.commons.cli.*;


public class Parser {
    private final Option ticketPrice = Option.builder()
            .argName("ticketPrice")
            .longOpt("ticketPrice")
            .desc("Price of the ticket")
            .hasArg(true)
            .build();

    private final Option customerAge = Option.builder()
            .argName("customerAge")
            .longOpt("customerAge")
            .desc("Age of the customer")
            .hasArg(true)
            .build();

    private final Option customerId = Option.builder()
            .argName("customerId")
            .longOpt("customerId")
            .desc("Id of the customer")
            .hasArg(true)
            .build();

    private final Option companyId = Option.builder()
            .argName("companyId")
            .longOpt("companyId")
            .desc("Id of the company")
            .hasArg(true)
            .build();

    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();

    public Parser() {
        options.addOption(ticketPrice)
                .addOption(customerAge)
                .addOption(customerId)
                .addOption(companyId);
    }

    public CommandLine parse(String[] args) throws ParseException {
        return parser.parse(options, args);
    }
}
