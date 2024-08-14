package com.ec.tt.bank.controllers;

import com.ec.tt.account.vo.common.Response;
import com.ec.tt.account.vo.transaction.CreateBankTransactionVo;
import com.ec.tt.account.vo.transaction.FindAllBankTransactionVo;
import com.ec.tt.account.vo.transaction.FindReportVo;
import com.ec.tt.account.vo.transaction.UpdateBankTransactionVo;
import com.ec.tt.transaction.services.IBankTransactionService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@RestController
@Lazy
@RequestMapping("api/v1/movimientos")
@Tag(name = "Transaction", description = "The transaction API")
public class BankTransactionController {
    @Lazy
    @Autowired
    private IBankTransactionService bankTransactionService;

    @GetMapping()
    @Operation(summary = "Get all bankTransaction")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of bankTransaction", content = {@Content(
            mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FindAllBankTransactionVo.class
    )))})})
    public ResponseEntity<Response<List<FindAllBankTransactionVo>>> findAll() {
        return new ResponseEntity<>(Response.<List<FindAllBankTransactionVo>>builder().data(bankTransactionService.findAll())
                .code(HttpStatus.OK.value())
                .message("SUCCESS").build(), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Create bankTransaction")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
            "Information needed to save bankTransaction", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateBankTransactionVo.class))})
    public ResponseEntity<Response<Boolean>> create(@RequestBody CreateBankTransactionVo data) {
        try {
            this.bankTransactionService.create(data);
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.CREATED.value())
                    .message("Created bankTransaction").build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error to create bankTransaction: " + e).build(), HttpStatus.OK);
        }
    }

    @PutMapping()
    @Operation(summary = "Update bankTransaction")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
            "Information needed to update bankTransaction", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = UpdateBankTransactionVo.class))})
    public ResponseEntity<Response<Boolean>> update(@RequestBody UpdateBankTransactionVo data) {
        try {
            this.bankTransactionService.update(data);
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.OK.value())
                    .message("Updated bankTransaction").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error to update bankTransaction: " + e).build(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete bankTransaction")
    @Parameter(in = ParameterIn.PATH, description = "id", name = "id",
            schema = @Schema(type = "long"), example = "1")
    public ResponseEntity<Response<Boolean>> delete(@NotBlank @PathVariable("id") Long id) {
        try {
            this.bankTransactionService.delete(id);
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.OK.value())
                    .message("Delete bankTransaction").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.<Boolean>builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Error to delete bankTransaction: " + e).build(), HttpStatus.OK);
        }
    }

    @GetMapping("/reportes")
    @Operation(summary = "Get report")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of report", content = {@Content(
            mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FindReportVo.class
    )))})})
    @Parameter(in = ParameterIn.PATH, description = "initialDate", name = "initialDate",
            schema = @Schema(type = "long"), example = "1")
    @Parameter(in = ParameterIn.PATH, description = "endDate", name = "endDate",
            schema = @Schema(type = "long"), example = "1")
    public ResponseEntity<Response<List<FindReportVo>>> findReport(@RequestParam("initialDate") Long initialDate,
                                                                   @RequestParam("endDate") Long endDate) {
        return new ResponseEntity<>(Response.<List<FindReportVo>>builder().data(bankTransactionService.findReport(
                        new Date(initialDate), new Date(endDate)))
                .code(HttpStatus.OK.value())
                .message("SUCCESS").build(), HttpStatus.OK);
    }
}
