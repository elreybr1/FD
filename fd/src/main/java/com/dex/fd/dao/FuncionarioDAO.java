package com.dex.fd.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.jdbc.GenericDAOImpl;
import com.dex.fd.model.Funcionario;
import com.dex.fd.service.exceprion.DBException;

public class FuncionarioDAO extends GenericDAOImpl<Funcionario>{
	
	private static final String table = "FUNCIONARIO";
	private static final String [] fields = {"EMAIL","TELEFONE","CIDADE","BAIRRO",
			"RUA","CEP","NUMERO","COMPLEMENTO","MATRICULA"};
	
	@Override
	public String getTableName() {
		return table;
	}
	
	/**
	 * @param parteNome
	 * @return
	 */
	public List<Funcionario> getByParteNome(String parteNome) throws DBException {
		List<Funcionario> list = new ArrayList<>();
		String sql = "SELECT * FROM " + table + " WHERE NOME LIKE ? AND DELETADA = false";
		
		try {
			PreparedStatement ps = super.getPreparedStatement(sql);
			ps.setString(1, "%"+parteNome+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.popular(rs));
			}
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		
		return list;
	}
	

	@Override
	public Funcionario popular(ResultSet rs) throws SQLException {
		Funcionario funcionario = new Funcionario();
		
		funcionario.setEmail(rs.getString("EMAIL"));
		funcionario.setTelefone(rs.getNString("TELEFONE"));
		funcionario.setCidade(rs.getString("CIDADE"));
		funcionario.setBairro(rs.getString("BAIRRO"));
		funcionario.setRua(rs.getString("RUA"));
		funcionario.setCep(rs.getString("CEP"));
		funcionario.setNumero(rs.getInt("NUMERO"));
		funcionario.setComplemento(rs.getString("COMPLEMENTO"));
		funcionario.setMatricula(rs.getString("MATRICULA"));
		
		return funcionario;
	}


	@Override
	public PreparedStatement preparePersistStatement(Funcionario entidade, Boolean isUpdate) throws SQLException {
		PreparedStatement ps = super.getPreparedStatement(
				super.getInsertClause(fields.length));
		
		ps.setString(1, entidade.getEmail());
		ps.setString(2, entidade.getTelefone());
		ps.setString(3, entidade.getCidade());
		ps.setString(4, entidade.getBairro());
		ps.setString(5, entidade.getRua());
		ps.setString(6, entidade.getCep());
		ps.setInt(7, entidade.getNumero());
		ps.setString(8, entidade.getComplemento());
		ps.setString(9, entidade.getMatricula());
		
		
		return ps;
	}

}
