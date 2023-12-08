package by.bsuir.wt2.webapp.classes.dao.commands;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The InsertCommand class is responsible for creating and executing an insert command on a database.
 *
 * @author Aleksej
 * @since 2023-12-07
 * @version 1.0
 *
 */
public class InsertionCommand {

    private final static String COMMAND_TYPE = "insert";

    private final static Logger logger = Logger.getLogger(InsertionCommand.class.getName());

    /** The insert command completing method
     * @param dbConnection the database connection to use
     * @param tableName the table insert to
     * @param attributes the attributes of object to insert
     * @param params the parameters of object to insert
     * @throws SQLException if an error occurs while executing the insert command
     */
    public static void completeCommand(Connection dbConnection, String tableName,
                                List<String> attributes, Map<String,Object> params)
                                throws SQLException {
        try {
            String attributesSet = "(";
            String paramsSet = "(";
            StringBuilder commandFormer = new StringBuilder();
            commandFormer.append(COMMAND_TYPE)
                    .append(" into ")
                    .append(tableName)
                    .append(" ");

            for (String attribute : attributes) {
                attributesSet = attributesSet.concat(attribute + ",");
                paramsSet = paramsSet.concat("?" + ",");
            }
            attributesSet = attributesSet.substring(0, attributesSet.length() - 1);
            attributesSet = attributesSet.concat(")");
            paramsSet = paramsSet.substring(0, paramsSet.length() - 1);
            paramsSet = paramsSet.concat(")");
            commandFormer.append(attributesSet)
                    .append(" values ")
                    .append(paramsSet)
                    .append(";");
            String command = commandFormer.toString();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command);
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(attributes.get(i)));
            }
            preparedStatement.execute();
            logger.log(Level.INFO,"Insertion command complete successfully");
        }catch (SQLException e){
            logger.log(Level.ERROR,"Error on completing insertion command");
            throw e;
        }
    }
}
