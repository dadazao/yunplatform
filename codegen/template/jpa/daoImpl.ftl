/*******************************************************************************
 * Licensed Materials - Property of ${globalVariables.company?cap_first} 
 * 
 * (C) Copyright ${globalVariables.company?cap_first} Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package ${mainTable.variables.package}.dao.impl;

import org.springframework.stereotype.Repository;

import ${mainTable.variables.package}.dao.${mainTable.variables.class}Dao;
import ${mainTable.variables.package}.model.${mainTable.variables.class};
import com.cloudstong.platform.core.dao.impl.BaseDaoJpa;

/**
 * @author ${globalVariables.developer}
 * Created on ${.now}
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:
 * 
 */
@Repository("${mainTable.variables.class?uncap_first}Dao")
public class ${mainTable.variables.class}DaoImpl extends BaseDaoJpa<${mainTable.variables.class}, Long> implements ${mainTable.variables.class}Dao  {

}
