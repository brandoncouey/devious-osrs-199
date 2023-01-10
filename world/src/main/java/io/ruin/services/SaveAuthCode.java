package io.ruin.services;

public class SaveAuthCode {

   /* public static void saveAuthBot(Player player) {
        if (!OfflineMode.enabled)
            return;
        Server.discordDb.execute(new DatabaseStatement() {
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM discordauth WHERE name = ? LIMIT 1", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                    statement.setString(1, player.getName());
                    resultSet = statement.executeQuery();
                    if (!resultSet.next()) {
                        resultSet.moveToInsertRow();
                        updateAuthBot(player, resultSet);
                        resultSet.insertRow();
                    }
                } finally {
                    DatabaseUtils.close(statement, resultSet);
                }
            }

            @Override
            public void failed(Throwable t) {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw, true);
                t.printStackTrace(pw);
                System.out.println("FAILED TO UPDATE AuthCode FOR: "+player.getName());
                System.out.println(sw.getBuffer().toString());
                /* do nothing */
   /*         }
        });
    }

    private static void updateAuthBot(Player player, ResultSet resultSet) throws SQLException {
        resultSet.updateString("name", player.getName());
        resultSet.updateString("auth", player.DiscordCode);
        System.err.println(player.getName()+"'s  AuthCode:"+player.DiscordCode+" Has been saved to the DB!");
    }

    public static void saveAuthUser(String discorduser, String code) {
        if (!OfflineMode.enabled)
            return;
        try
        {
            // create a java mysql database connection
            String myUrl = "jdbc:mysql://81.105.18.0/discordauth";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(myUrl, "Nightmare", "Toroth!754394");

            // create the java mysql update preparedstatement
            String query = "update `discordauth` set `discorduser` = ? where `auth` = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, discorduser);
            preparedStmt.setString(2, code);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
        }
    }*/

}
