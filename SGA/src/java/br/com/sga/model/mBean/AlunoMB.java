/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.model.mBean;

import br.com.sga.dao.DAOFactory;
import br.com.sga.dao.HibernateDAOImp;
import br.com.sga.model.bo.PessoaBO;
import br.com.sga.model.vo.Aluno;
import br.com.sga.model.vo.Endereco;
import br.com.sga.model.vo.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 *
 * @author DIGITACAOFUND
 */
@ManagedBean(name = "alunoBean")
@SessionScoped
public class AlunoMB implements Serializable, InterfaceManagedBean<Aluno> {

    private static final long serialVersionUID = 1L;
    
    private Aluno aluno;
    private Endereco endereco;
    private Pessoa pai, mae;
    private List<Aluno> listaAlunos;
    private UIInput nomePai = new UIInput();

    public AlunoMB() {
        pai = new Pessoa();
        mae = new Pessoa();
        aluno = new Aluno();
        endereco = new Endereco();
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Aluno> getListaAlunos() {
        if (listaAlunos == null || listaAlunos.isEmpty()) {
            setListaAlunos(getDAO().getEntityes());
        }
        return listaAlunos;
    }

    public void setListaAlunos(List<Aluno> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public Pessoa getPai() {
        return pai;
    }

    public void setPai(Pessoa pai) {
        this.pai = pai;
    }

    public Pessoa getMae() {
        return mae;
    }

    public void setMae(Pessoa mae) {
        this.mae = mae;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public UIInput getNomePai() {
        return nomePai;
    }

    public void setNomePai(UIInput nomePai) {
        this.nomePai = nomePai;
    }

    public void addAluno() {
        if (aluno.getId() == null || aluno.getId() == 0) {
            inserir();
        } else {
            atualizar();
        }
        setAluno(new Aluno());
    }

    public void verificaCpfPessoa() {
        PessoaBO pessoaBo = new PessoaBO();
        if (!pai.getCpf().equals("")) {
            pai = pessoaBo.selectPessoaByCpf(pai);
        } else if (!mae.getCpf().equals("")) {
            mae = pessoaBo.selectPessoaByCpf(mae);
        }
    }

    @Override
    public HibernateDAOImp getDAO() {
        return DAOFactory.instance(DAOFactory.FACTORY_IMPLEMENTATION).getAlunoDAO();
    }

    @Override
    public void setMessage(String msg, Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

//    public void 
    private void inserir() {
        getDAO().save(aluno);
        setMessage("Aluno inserido com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    private void atualizar() {
        getDAO().update(aluno);
        setMessage("Aluno atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
    }
}