package by.bsuir.wt2.webapp.classes.dao.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * The DeleteCommand class is responsible for creating and executing a delete command on a database.
 *
 * @author Aleksej
 * @since 2023-12-07
 * @version 1.0
 *
 */
public class DeleteCommand {

    private final static String COMMAND_TYPE = "delete";

    private final static Logger logger = Logger.getLogger(DeleteCommand.class.getName());
     /** The delete command completing method
     * @param dbConnection the database connection to use
     * @param tableName the table to delete from
     * @param attributes the attributes to delete by
     * @param params the parameters to use for the delete command
     * @throws SQLException if an error occurs while executing the delete command
     */
    public static void completeCommand(Connection dbConnection,String tableName,
                                List<String> attributes, Map<String,Object> params)
                                throws SQLException {
        try {
            StringBuilder commandFormer = new StringBuilder();
            commandFormer.append(COMMAND_TYPE)
                    .append(" from ")
                    .append(tableName)
                    .append(" where ")
                    .append("(");

            for (String attribute : attributes) {
                commandFormer.append(attribute + "=")
                             .append("?" + " and ");
            }
            commandFormer.delete(commandFormer.length() - 4, commandFormer.length() - 1);
            commandFormer.append(")")
                    .append(";");
            String command = commandFormer.toString();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command);
            for(int i = 0; i < params.size(); i++){
                preparedStatement.setObject(i + 1,params.get(attributes.get(i)));
            }
            preparedStatement.execute();
            logger.log(Level.INFO,"Delete command completed successfully");
        }catch (SQLException e){
            logger.log(Level.ERROR,"Error on competing delete command",e);
            throw e;
        }
    }
}

