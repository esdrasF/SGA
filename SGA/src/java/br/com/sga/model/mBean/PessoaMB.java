/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.model.mBean;

import br.com.sga.dao.DAOFactory;
import br.com.sga.dao.HibernateDAOImp;
import br.com.sga.model.bo.PessoaBO;
import br.com.sga.model.vo.Endereco;
import br.com.sga.model.vo.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author DIGITACAOFUND
 */
@ManagedBean(name = "pessoaBean")
@RequestScoped
public class PessoaMB implements Serializable, InterfaceManagedBean<Pessoa> {

    private Pessoa pessoa;
    private Endereco endereco;
    private List<Pessoa> listaPessoas;

    public PessoaMB() {
        endereco = new Endereco();
        pessoa = new Pessoa();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getListaPessoas() {
        if (listaPessoas == null || listaPessoas.isEmpty()) {
            setListaPessoas(getDAO().getEntityes());
        }
        return listaPessoas;
    }

    public void setListaPessoas(List<Pessoa> listaPessoas) {
        this.listaPessoas = listaPessoas;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public HibernateDAOImp getDAO() {
        return DAOFactory.instance(DAOFactory.FACTORY_IMPLEMENTATION).getPessoaDAOImp();
    }

    @Override
    public void setMessage(String msg, Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

    public String addPessoa() {
        getPessoa().setEndereco(endereco);

        PessoaBO pbo = new PessoaBO();

        if (pessoa.getId() == null || pessoa.getId() == 0) {
            if (pbo.validarCPF(pessoa)) {
                inserir();
                setPessoa(new Pessoa());
                setEndereco(new Endereco());
            } else {
                setMessage("CPF inválido.", FacesMessage.SEVERITY_WARN);
                return "";
            }
        } else {
            atualizar();
            setPessoa(new Pessoa());
            setEndereco(new Endereco());
        }
        return "/restrict/home.xhtml";
    }

    public void limparCampos() {
        setPessoa(new Pessoa());
        setEndereco(new Endereco());
    }

    private void inserir() {
        getDAO().save(pessoa);
        setMessage("Pessoa cadastrada com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    private void atualizar() {
        getDAO().save(pessoa);
        setMessage("Pessoa atualizada com sucesso.", FacesMessage.SEVERITY_INFO);
    }
}
