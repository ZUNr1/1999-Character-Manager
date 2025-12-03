package com.ZUNr1.dao;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.manager.CharacterManage;
import com.ZUNr1.model.*;
import com.ZUNr1.util.DataBaseConnection;
import com.ZUNr1.util.JsonConverter;
import com.ZUNr1.util.JsonDataLoader;
import com.fasterxml.jackson.core.type.TypeReference;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharactersMysqlDao {
    private static final String TABLE_NAME = "characters";
    public void loadToManageOverride(CharacterManage manage){
        loadToManage(manage,true);
    }
    public void loadToManageIncremental(CharacterManage manage){
        loadToManage(manage,false);
    }
    private void loadToManage(CharacterManage manage,boolean isClear){
        if (isClear){
            manage.clearAll();
        }//完全覆盖就是清空
        int addCount = 0;
        List<Characters> charactersList = findAll();
        for (Characters character : charactersList){
            if (!isClear && manage.findById(character.getId()) != null){
                //如果没有清空，同时manage存的角色找得到，就跳过不录入
                continue;
            }
            manage.addCharacters(character);
            addCount++;
        }
        System.out.println("从MySQL成功加载 " + addCount + " 个角色");
    }
    private List<Characters> findAll(){
        List<Characters> charactersList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id";
        //我们先写好sql指令，后面使用PreparedStatement注入
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            //Connection是通过DriverManager获取的，数据库与代码之间的桥梁
            //PreparedStatement接口用于执行预编译的 SQL 语句，就是直接在数据库执行，使用Connection的方法
            //ResultSet 是 JDBC 中表示数据库查询结果的对象。它类似于一个表格，包含查询返回的数据行。可以通过方法移动行数
            //注意三者都需要关闭，所以使用try-and-resource代码块
            //关闭顺序是先关最后声明的resultSet
            while (resultSet.next()){
                //rs.next()：将结果集指针移动到下一行,第一次调用是移到第一行，每次处理一行，就一个角色对象
                Characters character = convertFromResultSet(resultSet);
                charactersList.add(character);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return charactersList;
    }
    private Characters convertFromResultSet(ResultSet resultSet) throws SQLException{

        JsonDataLoader.CharactersJson json = new JsonDataLoader.CharactersJson();
        //我们将ResultSet获得的数据先存在CharactersJson中转里面
        json.id = resultSet.getString("id");
        //ResultSet这个先指定行，然后可以读取这个行的字段，getInt可以返回int，同理像getBoolean这样

        json.name = resultSet.getString("name");
        json.enName = resultSet.getString("en_name");
        json.gender = parseEnum(resultSet.getString("gender"),Gender.class,Gender.OTHER);
        //枚举类型可以使用默认实现的valueOf，直接String转换为对应的枚举类，但是我们要明白，要是数据库的枚举是非法的不存在的，会抛异常，要捕获
        json.isCustom = resultSet.getBoolean("is_custom");
        json.creator = resultSet.getString("creator");
        json.afflatus = parseEnum(resultSet.getString("afflatus"), Afflatus.class,Afflatus.STAR);
        json.damageType = parseEnum(resultSet.getString("damage_type"), DamageType.class,DamageType.GENESIS);
        json.rarity = resultSet.getInt("rarity");

        //下面是对json格式的数据库列的处理，我用了自制类JsonConverter，方便转换
        String attributesJson = resultSet.getString("attributes");
        //mysql里面的json也是使用getString转换为String
        if (attributesJson != null){
            json.attributes = JsonConverter.fromJson(attributesJson, Attributes.class);
            //jackson会自动填充，详情见JsonConverter的注释
        }
        String skillsJson = resultSet.getString("skills");
        if (skillsJson != null){
            json.skills = JsonConverter.fromJson(skillsJson, Skills.class);
        }
        String usedTermJson = resultSet.getString("used_term");
        if (usedTermJson != null){
            TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {};
            //TypeReference就是这样定义的，这是我设计的另一个重载的fromJson方法，为了保留泛型信息而不是List<Object>
            json.usedTerm = JsonConverter.fromJson(usedTermJson,typeReference);
        }
        String inheritanceJson = resultSet.getString("inheritance");
        if (inheritanceJson != null){
            json.inheritance = JsonConverter.fromJson(inheritanceJson, Inheritance.class);
        }
        String portraitJson = resultSet.getString("portrait");
        if (portraitJson != null){
            json.portrait = JsonConverter.fromJson(portraitJson, Portrait.class);
        }
        String euphoriaJson = resultSet.getString("euphoria");
        if (euphoriaJson != null){
            TypeReference<List<Euphoria>> typeReference = new TypeReference<List<Euphoria>>() {};
            //同样，TypeReference可以嵌套，可以读自制的类,这是ObjectMapper的readValue实现的读懂TypeReference
            json.euphoria = JsonConverter.fromJson(euphoriaJson,typeReference);
        }
        String otherInformationJson = resultSet.getString("other_information");
        if (otherInformationJson != null){
            json.otherInformation = JsonConverter.fromJson(otherInformationJson, OtherInformation.class);
            //ObjectMapper的readValue同样可以读取类中的类，但是前提是都需要无参公开的构造器，jackson是先无参构造类再通过反射一个个字段填入
        }
        Characters.CharactersBuilder charactersBuilder = new Characters.CharactersBuilder(
                json.id,json.name,json.enName,json.gender, json.isCustom,json.creator
        );
        Characters character = charactersBuilder.attributes(json.attributes)
                .afflatus(json.afflatus)
                .damageType(json.damageType)
                .rarity(json.rarity)
                .inheritance(json.inheritance)
                .portrait(json.portrait)
                .euphoria(json.euphoria)
                .skills(json.skills)
                .usedTerm(json.usedTerm)
                .otherInformation(json.otherInformation)
                .build();
        return character;
    }
    private <T extends Enum<T>> T parseEnum(String value,Class<T> enumClass,T defaultValue){//读取的时候用
        if (value == null || value.trim().isEmpty()){
            return defaultValue;
        }
        try {
            return Enum.valueOf(enumClass,value);
            //假如数据库有未定义的非法枚举，这代码会抛异常，虽然不强制要求处理，但是还是处理一下，出错就返回默认值
        }catch (IllegalArgumentException e){
            System.err.println("枚举转换失败，使用默认值: " + value + " → " + defaultValue.name());
            return defaultValue;
        }
    }
    public void saveFromManagerOverride(CharacterManage manage){
        saveFromManager(manage,true);
    }
    public void saveFromManagerIncremental(CharacterManage manage){
        saveFromManager(manage,false);
    }
    public void saveFromManager(CharacterManage manage,boolean isClear){
        //事务是保证一组数据库操作要么全部成功、要么全部失败的机制，遵循ACID特性（原子性、一致性、隔离性、持久性），以确保数据完整性与一致性。
        //JDBC原生事务 通过Connection对象控制，需手动关闭自动提交并调用commit()或rollback()
        //就是当我们对数据库进行处理，比如删除所有，这个时候要是发生异常，数据库就丢失了所有信息，我们就需要事务
        //connection.setAutoCommit(false);开始事务，出现问题connection.rollback();可以回滚到开始事务的时候
        //connection.commit();就是提交事务，结束事务模式
        //回滚后通常是直接停止
        List<Characters> charactersList = manage.findAllCharacters();
        Connection connection = null;
        //这里我们不使用try-with-resources关闭Connection
        //try-with-resources 会在 try 块结束时立即关闭资源
        //但我们需要在整个方法执行期间保持连接开启，以便控制事务
        //我们需要catch到错误进行回滚，要在执行catch时不能关闭
        try {
            connection = DataBaseConnection.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            // todo 搞懂隔离级别相关的问题是什么
            connection.setAutoCommit(false);
            //setAutoCommit(false): 禁用自动提交，开启事务模式。这样所有的 SQL 操作都在一个事务中，要么全部成功，要么全部回滚
            if (isClear) {
                String deleteSql = "DELETE FROM " + TABLE_NAME;
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                    //这里用了try-and-resource处理PrepareStatement
                    int deleteRow = preparedStatement.executeUpdate();
                    //这个方法是执行一次存入的sql指令，返回一个int表示影响的行数
                    //与executeQuery()区分，这个是在查询的时候使用
                    //这个方法也是可以安全防止sql注入的
                    System.out.println("删除了" + deleteRow + "行记录");
                }
            }
            //不管有没有clear，后面的保存角色都应该做
            int savedCount = 0;
            for (Characters character : charactersList) {
                if (saveCharacters(character, connection)) {
                    savedCount++;
                }
            }
            connection.commit();
            //这里提交事务，运行到这里还没有出错就可以了，防止删除了却没有保存导致数据丢失
            System.out.println("成功保存 " + savedCount + " 个角色到MySQL");
        }catch (SQLException e){
            if (connection != null){
                try {
                    connection.rollback();
                    //这里捕获到错误就回滚事务到最开始，撤销所有未提交的更改
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
            System.err.println("保存到MySQL失败: " + e.getMessage());
            e.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                    //记得，没有使用try-and-resource就要finally里面关闭流
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean saveCharacters(Characters character,Connection connection) throws SQLException{
        if (isCharactersExists(character.getId(), connection)){
            return updateCharacters(character,connection);
        }else {
            return insertCharacters(character,connection);
        }
    }
    private boolean isCharactersExists(String id,Connection connection) throws SQLException{
        //异常交给上级处理
        if (id == null || id.trim().isEmpty() || connection == null){
            return false;
        }
        String selectSql = "SELECT id FROM " + TABLE_NAME + " WHERE id = ?";
        //千万不要在sql里面写分号，同时不要直接把用户输入的变量直接放入sql语句，变量可能是恶意的有实际效果的sql语句
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)){
            preparedStatement.setString(1,id);
            //preparedStatement.setString(int parameterIndex, String value);
            //这句话的意思是就是在第一个问号？处使用id替换
            //使用这个方法主要是防止sql恶意注入，比如外界输入的id要是包含--，--在sql里面会被解读为注释，就会注释掉后续的语句导致不可控后果
            //而setString方法可以理解参数信息，将id解读为纯粹的字符串，就会修改sql语句（比如‘就会变为’‘避免产生歧义）
            //另外，多次执行同一sql语句时，会更快，会有格式的缓存
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                //executeQuery()一般用来执行查询语句，返回ResultSet对象
                //executeUpdate()一般执行更新语句，返回修改了多少行
                return resultSet.next();
            }
        }
    }
    private boolean insertCharacters(Characters character,Connection connection) throws SQLException{
        String insertSql = "INSERT INTO " + TABLE_NAME + " (" +
                "id,name,en_name,is_custom,creator,rarity,gender,afflatus,damage_type," +
                "attributes,skills,used_term,inheritance,portrait,euphoria,other_information) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //问好同样是用于后续setString填入
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)){
            setPreparedStatementValue(character,preparedStatement,true);
            //上行就是实际的转换方法
            return preparedStatement.executeUpdate() > 0;
        }

    }
    private boolean updateCharacters(Characters character,Connection connection) throws SQLException{
        String updateSql = "UPDATE " + TABLE_NAME + " SET " +
                "name = ?, " +
                "en_name = ?, " +
                "is_custom = ?, " +
                "creator = ?, " +
                "rarity = ?, " +
                "gender = ?, " +
                "afflatus = ?, " +
                "damage_type = ?, " +
                "attributes = ?, " +
                "skills = ?, " +
                "used_term = ?, " +
                "inheritance = ?, " +
                "portrait = ?, " +
                "euphoria = ?, " +
                "other_information = ? " +
                "WHERE id = ?";//where语句放在最后
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)){
            setPreparedStatementValue(character,preparedStatement,false);
            preparedStatement.setString(16, character.getId());
            //第16个参数是id字段
            return preparedStatement.executeUpdate() > 0;
        }
    }
    private void setPreparedStatementValue(Characters character, PreparedStatement preparedStatement,boolean forInsert)
            throws SQLException{
        //这个方法是将角色的字段转换为sql语句value里面对应的值
        int index = 1;
        if (forInsert){
            preparedStatement.setString(index++, character.getId());
        }
        preparedStatement.setString(index++, character.getName());
        preparedStatement.setString(index++,character.getEnName());
        preparedStatement.setBoolean(index++,character.isCustom());
        //boolean类型用setBoolean
        preparedStatement.setString(index++, character.getCreator());
        preparedStatement.setInt(index++,character.getRarity());

        //枚举类需要处理空指针异常，对于枚举类特有的默认实现的name方法会抛这个异常，我们用单独的方法处理
        setEnumField(preparedStatement, index++, character.getGender(),"OTHER");
        setEnumField(preparedStatement, index++, character.getAfflatus(),"STAR");
        setEnumField(preparedStatement, index++, character.getDamageType(),"GENESIS");

        //json字段要写个方法单独处理
        setJsonField(preparedStatement,index++,character.getAttributes());
        setJsonField(preparedStatement,index++,character.getSkills());
        setJsonField(preparedStatement,index++,character.getUsedTerm());
        setJsonField(preparedStatement,index++,character.getInheritance());
        setJsonField(preparedStatement,index++,character.getPortrait());
        setJsonField(preparedStatement,index++,character.getEuphoria());
        setJsonField(preparedStatement,index++,character.getOtherInformation());
        //我们不需要处理创建时间和更新时间，因为我们在mysql数据库中指定了这两参数的自动更新逻辑


    }
    private void setEnumField(PreparedStatement preparedStatement,int index,Enum<?> enumValue,String defaultValue)//写入的时候用
            throws SQLException{
        if (enumValue != null){
            preparedStatement.setString(index,enumValue.name());
            //一般的类是没有默认实现的name方法的，枚举类有，返回的是枚举的字符串(全大写的那个)
        }else {
            //这里不能一并设置为null，有默认值的，我们mysql指定了not null
            preparedStatement.setString(index,defaultValue);
        }

    }
    private void setJsonField(PreparedStatement preparedStatement,int index,Object value)
            throws SQLException{
        //吐槽，向上抛出异常确实不错，不然要写好多catch
        if (value != null){
            String json = JsonConverter.toString(value);
            //自制类的方法，将对象使用ObjectMapper的writeValueAsString方法获得String
            preparedStatement.setString(index,json);
        }else {
            preparedStatement.setNull(index, Types.VARCHAR);
            //setNull方法也是需要两个，一个是对应的问号，另一个是这个字段在mysql里面的类型，这里是VARCHAR
        }

    }
    public int getCount(){
        String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(countSql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            //有注入sql语句就用PreparedStatement，有接收就加上ResultSet
            if (resultSet.next()){
                //next即游标向下移动一行，存在数据返回true
                return resultSet.getInt(1);
                //get第一列的int类型数据
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteById(String id){
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)){
            preparedStatement.setString(1,id);
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("删除了 " + affectedRows + " 行");
            return affectedRows > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private void clearTable(){
        //这个方法要小心，虽然TRUNCATE TABLE块，但是不能用事务回滚，清空了就找不回来了，所以我写了但是不用，最好是不使用
        String clearSql = "TRUNCATE TABLE " + TABLE_NAME;
        //与DELETE FROM相比，TRUNCATE TABLE更加高效，清空所有数据用这个，更快
        //但是这个不能rollback回滚，需要权限高
        try (Connection connection = DataBaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(clearSql)){
            preparedStatement.executeUpdate();
            System.out.println("已清空数据库表");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
