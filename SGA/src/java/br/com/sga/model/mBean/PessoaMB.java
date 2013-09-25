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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author DIGITACAOFUND
 */
@ManagedBean(name = "pessoaBean")
@SessionScoped
public class PessoaMB implements Serializable, InterfaceManagedBean<Pessoa> {

    private Pessoa pessoa;
    private List<Pessoa> listaPessoas;
    private boolean renderedPanelNovoCadastro = false;

    public PessoaMB() {
        pessoa = new Pessoa();
        pessoa.setEndereco(new Endereco());
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

        PessoaBO pbo = new PessoaBO();

        if (pbo.validarCPF(pessoa)) {

            if (pessoa.getId() == null || pessoa.getId() == 0) {
                if (!pbo.isExistCpfDB(pessoa)) {
                    inserir();
                    setPessoa(new Pessoa());
                    getPessoa().setEndereco(new Endereco());
                    setListaPessoas(null);
                    setRenderedPanelNovoCadastro(false);
                } else {
                    setMessage("CPF já cadastrado na base de dados.", FacesMessage.SEVERITY_WARN);
                }
            } else {
                atualizar();
                setPessoa(new Pessoa());
                getPessoa().setEndereco(new Endereco());
                setListaPessoas(null);
                setRenderedPanelNovoCadastro(false);
            }

        } else {
            setMessage("CPF inválido.", FacesMessage.SEVERITY_WARN);
        }
    }

    public void excluirPessoa() {
        getDAO().remove(pessoa);
        setPessoa(new Pessoa());
        setListaPessoas(null);
        setMessage("Registro excluido com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    public String editarPessoa() {
        return "/restrict/cadastro_pessoa.xhtml";
    }

    public String novoCadastro() {
        setPessoa(new Pessoa());
        return "/restrict/cadastro_pessoa.xhtml";
    }

    public void cancelarNovoCadastro() {
        setRenderedPanelNovoCadastro(false);
    }

    public void limparCampos() {
        setPessoa(new Pessoa());
        getPessoa().setEndereco(new Endereco());
    }

    private void inserir() {
        getDAO().save(pessoa);
        setMessage("Pessoa cadastrada com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    private void atualizar() {
        getDAO().update(pessoa);
        setMessage("Pessoa atualizada com sucesso.", FacesMessage.SEVERITY_INFO);
    }
}
