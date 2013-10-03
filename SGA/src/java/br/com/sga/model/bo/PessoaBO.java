/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.model.bo;

import br.com.sga.dao.DAOFactory;
import br.com.sga.dao.imp.PessoaDAOImp;
import br.com.sga.model.vo.Pessoa;
import java.util.ArrayList;
import org.hibernate.Criteria;

/**
 *
 * @author DIGITACAOFUND
 */
public class PessoaBO {
    
    private PessoaDAOImp pessoaDAO;
    
    private String cpfOriginal;
    private String cpfCalculado;
    ArrayList<String> cpfs = new ArrayList<String>();
    
    public PessoaBO() {
        
        pessoaDAO = DAOFactory.instance(DAOFactory.FACTORY_IMPLEMENTATION).getPessoaDAOImp();
        
        cpfs.add("111.111.111-11");
        cpfs.add("222.222.222-22");
        cpfs.add("333.333.333-33");
        cpfs.add("444.444.444-44");
        cpfs.add("555.555.555-55");
        cpfs.add("666.666.666-66");
        cpfs.add("777.777.777-77");
        cpfs.add("888.888.888-88");
        cpfs.add("999.999.999-99");
        cpfs.add("000.000.000-00");
    }
    
    public boolean validarCPF(Pessoa pessoa) {
        
        if (!isCpfList(pessoa.getCpf())) {
            cpfOriginal = pessoa.getCpf();
            
            ArrayList<Integer> cpf = limpaCaracteresCPF(pessoa.getCpf());
            
            cpf = calcularDigitoVerificador1(cpf);
            cpf = calcularDigitoVerificador2(cpf);
            cpfCalculado = transformarCpfString(cpf);
            
            return comparaCpf();
        }
        return false;
    }
    
    public boolean isExistCpfDB(Pessoa pessoa) {
        
        boolean retorno = false;
        Pessoa p = null;
        p = pessoaDAO.selectByCPF(pessoa);
        
        if(p != null) {
            retorno = true;
        }
        return retorno;
    }
    
    public Pessoa selectPessoaByCpf(Pessoa p) {
        return pessoaDAO.selectByCPF(p);
    }
    
    private ArrayList limpaCaracteresCPF(String s) {
        String[] string = s.replace(".", "").replace("-", "").split("");
        
        ArrayList<Integer> arrayCpf = new ArrayList();
        
        for (int i = 0; i < 10; i++) {
            if (!string[i].equals("")) {
                arrayCpf.add(Integer.parseInt(string[i]));
            }
        }
        return arrayCpf;
    }
    
    private ArrayList<Integer> calcularDigitoVerificador1(ArrayList<Integer> cpf) {
        int soma = 0;
        int divisor = 10;
        int digitoVerificador1;
        
        for (Integer digito : cpf) {
            soma += digito.intValue() * divisor;
            divisor--;
        }
        
        int resto = soma % 11;
        
        if (resto < 2) {
            digitoVerificador1 = 0;
        } else {
            digitoVerificador1 = 11 - resto;
        }
        
        cpf.add(digitoVerificador1);
        return cpf;
    }
    
    private ArrayList<Integer> calcularDigitoVerificador2(ArrayList<Integer> cpf) {
        
        int soma = 0;
        int divisor = 11;
        int digitoVerificador1;
        
        for (Integer digito : cpf) {
            soma += digito.intValue() * divisor;
            divisor--;
        }
        
        int resto = soma % 11;
        
        if (resto < 2) {
            digitoVerificador1 = 0;
        } else {
            digitoVerificador1 = 11 - resto;
        }
        
        cpf.add(digitoVerificador1);
        return cpf;
    }
    
    private String transformarCpfString(ArrayList<Integer> cpf) {
        
        String s = "" + cpf.get(0) + cpf.get(1) + cpf.get(2) + "."
                + cpf.get(3) + cpf.get(4) + cpf.get(5) + "."
                + cpf.get(6) + cpf.get(7) + cpf.get(8) + "-"
                + cpf.get(9) + cpf.get(10);
        return s;
    }
    
    private boolean isCpfList(String cpf) {
        return cpfs.contains(cpf);
    }
    
    private boolean comparaCpf() {
        if (cpfOriginal.equals(cpfCalculado)) {
            return true;
        }
        return false;
    }

//    public static void main(String[] args) {
//        Pessoa p = new Pessoa();
//        p.setCpf("222.222.222-22");
//        PessoaBO bo = new PessoaBO();
//        if (bo.validarCPF(p)) {
//            System.out.println("CPF " + p.getCpf() + " é válido.");
//        } else {
//            System.out.println("CPF " + p.getCpf() + " não é válido.");
//        }
//    }
}
