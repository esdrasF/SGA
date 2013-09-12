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
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
    private boolean renderedPanelNovoCadastro = false;

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

    public boolean isRenderedPanelNovoCadastro() {
        return renderedPanelNovoCadastro;
    }

    public void setRenderedPanelNovoCadastro(boolean renderedPanelNovoCadastro) {
        this.renderedPanelNovoCadastro = renderedPanelNovoCadastro;
    }

    @Override
    public HibernateDAOImp getDAO() {
        return DAOFactory.instance(DAOFactory.FACTORY_IMPLEMENTATION).getPessoaDAOImp();
    }

    @Override
    public void setMessage(String msg, Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

    public void addPessoa() {
        getPessoa().setEndereco(endereco);

        PessoaBO pbo = new PessoaBO();

        if (pessoa.getId() == null || pessoa.getId() == 0) {
            if (pbo.validarCPF(pessoa)) {
                inserir();
                setPessoa(new Pessoa());
                setEndereco(new Endereco());
                setListaPessoas(getDAO().getEntityes());
                setRenderedPanelNovoCadastro(false);
            } else {
                setMessage("CPF inválido.", FacesMessage.SEVERITY_WARN);
            }
        } else {
            atualizar();
            setPessoa(new Pessoa());
            setEndereco(new Endereco());
            setListaPessoas(getDAO().getEntityes());
            setRenderedPanelNovoCadastro(false);
        }
    }

    public void excluirPessoa() {
        getDAO().remove(pessoa);
        setPessoa(new Pessoa());
        setListaPessoas(null);
        setMessage("Registro excluido com sucesso.", FacesMessage.SEVERITY_INFO);
    }
    
    public void cancelarNovoCadastro(){
        setRenderedPanelNovoCadastro(false);
    }
    
    public void novoCadastro() {
        setRenderedPanelNovoCadastro(true);
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
