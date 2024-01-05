package br.com.bank.rest;

import br.com.bank.persistence.dto.AccountDto;
import br.com.bank.persistence.model.Account;
import br.com.bank.service.AccountService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/accountusers")
public class AccountController {

    private final AccountService service;

    @Inject
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GET
    public Response getAllAccounts() {
        List<Account> accounts = this.service.getAccounts();
        return Response.status(Response.Status.OK).entity(accounts).build();
    }

    @GET
    @Path("/{id}")
    public Response getAccount(@PathParam("id") Long id) {
        Account account = this.service.getAccount(id).orElse(new Account());
        return Response.status(Response.Status.OK).entity(account).build();
    }

    @POST
    @Transactional
    public Response addAccount(AccountDto userData) {
        try {
            Account createdAccount = this.service.addAccount(userData);
            if (userData.getValorDeposito() != null) {
                this.service.deposit(createdAccount.getNumConta(), userData.getValorDeposito());
            }
            return Response.status(Response.Status.CREATED).entity(createdAccount).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar a conta").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response editAccount(@PathParam("id") Long id, AccountDto data) {
        try {
            Account updatedAccount = this.service.editAccount(id, data).orElseThrow(() -> new NotFoundException("Conta não identificada"));
            return Response.status(Response.Status.OK).entity(updatedAccount).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao editar a conta").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteAccount(@PathParam("id") Long id) {
        this.service.deleteAccount(id);
        return Response.status(Response.Status.OK).build();
    }


    @POST
    @Path("/deposito")
    @Transactional
    public Response deposit(AccountDto depositoData) {
        try {
            if (depositoData.getNumConta() == null || depositoData.getValorDeposito() == null) {
                throw new BadRequestException("O número da conta e valor do depósito são obrigatórios");
            }
            this.service.deposit(depositoData.getNumConta(), depositoData.getValorDeposito());
            return Response.status(Response.Status.OK).entity("Depósito realizado").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao realizar o depósito. Detalhes: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/saque")
    @Transactional
    public Response sacar(AccountDto saqueData) {
        try {
            if (saqueData.getNumConta() == null || saqueData.getValorSaque() == null) {
                throw new BadRequestException("O número da conta e valor do saque são obrigatórios");
            }
            this.service.sacar(saqueData.getNumConta(), saqueData.getValorSaque());
            return Response.status(Response.Status.OK).entity("Saque realizado").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RuntimeException e) {
            String mensagemErro = e.getMessage();
            String mensagemSaldoInsuficiente = "Saldo insuficiente para realizar o saque.";
            if (mensagemErro.contains(mensagemSaldoInsuficiente)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(mensagemSaldoInsuficiente).build();
            } else {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Erro ao realizar o saque. Detalhes: " + mensagemErro)
                        .build();
            }
        }

    }
}
