package fr.emse.numericwall.repository;


import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.generator.ValueGenerators;
import com.ninja_squad.dbsetup.operation.Operation;

public class DataTest {

    public static final Operation DELETE_ALL = deleteAllFrom(
            "QrCode", "Work", "Category", "User_Authority", "Authority", "User"
    );

    public static Operation INSERT_AUTHORITIES = Operations.sequenceOf(
            insertInto("Authority")
                    .withGeneratedValue("id", ValueGenerators.sequence())
                    .columns("name")
                    .values("ADMIN")
                    .values("PUBLIC")
                    .build()
    );

}
