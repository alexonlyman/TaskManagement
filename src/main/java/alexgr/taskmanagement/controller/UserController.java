package alexgr.taskmanagement.controller;


import alexgr.taskmanagement.dto.user.UpdateUser;
import alexgr.taskmanagement.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * REST controller for managing user-related operations.
 * <p>
 * This controller provides endpoints for updating user information and retrieving a list of users.
 * </p>
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "UserController", description = "Контроллер для управления информацией о пользователях")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;


    /**
     * Updates user information.
     *
     * @param updateUser the {@link UpdateUser} object containing the updated user details
     * @return a {@link ResponseEntity} containing the updated {@link UpdateUser} object
     */
    @Operation(summary = "Обновление информации о пользователе", description = "Обновляет данные пользователя, используя переданный объект UpdateUser.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация успешно обновлена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUser.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content)
    })
    @PostMapping("/updateInfo")
    public ResponseEntity<UpdateUser> updateUserInfo(@RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok(userService.updateUserInfo(updateUser));
    }

}
