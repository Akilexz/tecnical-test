package com.ec.tt.bank.controllers;

import com.ec.tt.account.services.IAccountService;
import com.ec.tt.account.vo.account.CreateAccountVo;
import com.ec.tt.account.vo.account.FindAllAccountVo;
import com.ec.tt.account.vo.account.UpdateAccountVo;
import com.ec.tt.account.vo.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Lazy
@RequestMapping("api/v1/cuentas")
@Tag(name = "Account", description = "The account API")
public class AccountController {
    @Lazy
    @Autowired
    private IAccountService accountService;

    @GetMapping()
    @Operation(summary = "Get all account")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of account", content = {@Content(
            mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FindAllAccountVo.class
    )))})})
    public ResponseEntity<Response<List<FindAllAccountVo>>> findAll() {
        return new ResponseEntity<>(Response.<List<FindAllAccountVo>>builder().data(accountService.findAll())
                .code(HttpStatus.OK.value())
                .message("SUCCESS").build(), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Create account")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
            "Information needed to save account", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateAccountVo.class))})
        public ResponseEntity<Response<Boolean>> create(@RequestBody CreateAccountVo data) {
        try {
            this.accountService.create(data);
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.CREATED.value())
                    .message("Created account").build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error to create account: " + e).build(), HttpStatus.OK);
        }
    }

    @PutMapping()
    @Operation(summary = "Update account")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
            "Information needed to update account", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateAccountVo.class))})
    public ResponseEntity<Response<Boolean>> update(@RequestBody UpdateAccountVo data) {
        try {
            this.accountService.update(data);
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.OK.value())
                    .message("Updated account").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error to update account: " + e).build(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account")
    @Parameter(in = ParameterIn.PATH, description = "id", name = "id",
            schema = @Schema(type = "long"), example = "1")
    public ResponseEntity<Response<Boolean>> delete(@NotBlank @PathVariable("id") Long id) {
        try {
            this.accountService.delete(id);
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.OK.value())
                    .message("Delete account").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error to delete account").build(), HttpStatus.OK);
        }
    }
}
