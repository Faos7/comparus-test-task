package com.example.comparus.testtask.web.controller;

import com.example.comparus.testtask.data.dto.ErrorData;
import com.example.comparus.testtask.data.dto.UserData;
import com.example.comparus.testtask.data.dto.UserSearchFilter;
import com.example.comparus.testtask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User API")
@ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(
        schema = @Schema(implementation = ErrorData.class)))
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get info for all users in connected databases",
            description = "Retrieve a list of user data based on the provided search filters",
            tags = {"User"}
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of user data",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = com.example.comparus.testtask.data.dto.UserData.class)))})
    })

    @Parameters(value = {
            @Parameter(in = ParameterIn.QUERY, description = "User ID", name = "id",
                    schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.QUERY, description = "Username", name = "username",
                    schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.QUERY, description = "User's first name", name = "name",
                    schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.QUERY, description = "User's last name", name = "surname",
                    schema = @Schema(type = "string"))
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserData>> getUsers(UserSearchFilter userSearchFilter) {
        return ResponseEntity.ok(userService.getUserDataList(userSearchFilter));
    }
}