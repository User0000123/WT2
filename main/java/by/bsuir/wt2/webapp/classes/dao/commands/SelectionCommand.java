package by.bsuir.wt2.webapp.classes.dao.commands;

import java.sql.*;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class SelectionCommand {

    private final static String COMMAND_TYPE = "select";

    private final static Logger logger = Logger.getLogger(SelectionCommand.class.getName());

    /** The selection command completing method
     * @param dbConnection the database connection to use
     * @param tableName the table from selection is completing
     * @param attributes the attributes of object to select
     * @param params the parameters of object to se
     * @throws SQLException if an error occurs while executing the insert command
     */
    public static ResultSet completeCommand(Connection dbConnection, String tableName,
                                            String selectionAttribute, List<String> attributes,
                                            Map<String,Object> params) throws SQLException {
        try {
            StringBuilder commandFormer = new StringBuilder();
            commandFormer.append(COMMAND_TYPE)
                    .append(" ")
                    .append(selectionAttribute)
                    .append(" from ")
                    .append(tableName)
                    .append(" where ")
                    .append("(");
            for (String attribute : attributes) {
                commandFormer.append(attribute + "=" + "?")
                        .append(" and ");
            }
            commandFormer.delete(commandFormer.length() - 4, commandFormer.length());
            commandFormer.append(")")
                    .append(";");
            String command = commandFormer.toString();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for(int i = 0;i < params.size();i++){
                preparedStatement.setObject(i + 1, params.get(attributes.get(i)));
            }
            ResultSet selectionResult = preparedStatement.executeQuery();
            logger.log(Level.DEBUG,"Selection command completed successfully");
            return selectionResult;
        }catch (SQLException e){
            logger.log(Level.ERROR,"Error while completing selection command");
            throw e;

        }
    }

    public static ResultSet completeCommandForPagination(Connection dbConnection, String tableName,
                                                         String selectionAttribute, int offset, int limit)
                                                         throws SQLException{
        try {
            StringBuilder commandFormer = new StringBuilder();
            commandFormer.append(COMMAND_TYPE)
                    .append(" ")
                    .append(selectionAttribute)
                    .append(" from ")
                    .append(tableName)
                    .append(" limit ")
                    .append(limit)
                    .append(" offset ")
                    .append(offset)
                    .append(";");
            String command = commandFormer.toString();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet selectionResult = preparedStatement.executeQuery();
            logger.log(Level.DEBUG,"Selection command completed successfully");
            return selectionResult;
        }catch (SQLException e){
            logger.log(Level.ERROR,"Error while completing selection command");
            throw e;
        }
    }

    public static int selectTableRowsCount(Connection dbConnection,String tableName) throws SQLException{
        try {
            StringBuilder commandFormer = new StringBuilder();
            commandFormer.append(COMMAND_TYPE)
                         .append(" count(*) ")
                         .append(" as size ")
                         .append(" from ")
                         .append(tableName)
                         .append(";");
            String command = commandFormer.toString();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet selectionResult = preparedStatement.executeQuery();
            logger.log(Level.DEBUG,"Selection command completed successfully");
            selectionResult.next();
            return selectionResult.getInt("size");
        }catch (SQLException e){
            logger.log(Level.ERROR,"Error while completing selection command");
            throw e;
        }
    }
}
