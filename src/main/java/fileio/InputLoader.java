package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.CommandInput;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import users.User;
import users.UserDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic skeleton for loading input JSON file as a map.
 * Students should implement deeper parsing themselves.
 */
@Getter
public final class InputLoader {
    private final ArrayList<CommandInput> commands;
    private final List<UserDTO> users;

    public InputLoader(final String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.commands = mapper.readValue(
                new File(filePath),
                mapper.getTypeFactory().constructCollectionType(List.class, CommandInput.class)
        );

        String usersPath = "input/database/users.json";
        this.users = mapper.readValue(
                new File(usersPath),
                mapper.getTypeFactory().constructCollectionType(List.class, UserDTO.class)
        );
    }
}