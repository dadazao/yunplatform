<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">  
<mapper namespace="${mainTable.variables.package}.model.${mainTable.variables.class}">  
    <resultMap id="entityBaseMap" type="com.cloudstong.platform.core.model.EntityBase">  
     	<result property="createBy" column="comm_createBy"/>
        <result property="createDate" column="comm_createDate"/>
        <result property="updateBy" column="comm_updateBy"/>
        <result property="updateDate" column="comm_updateDate"/>
        <result property="isDeleted" column="comm_mark_for_delete"/> 
    </resultMap>  
    
    <resultMap id="${mainTable.variables.class?uncap_first}ResultMap" type="${mainTable.variables.package}.model.${mainTable.variables.class}" extends="entityBaseMap" >    
        <id property="id" column="id"/>
		<#list mainTable.columns as col>
		<result property="${col.colName}" column="${col.name}"/>    
		</#list>
		<#if mainTable.sub>
		<result property="${mainTable.parentClassName?uncap_first}Id" column="${mainTable.foreignKey}"/>
		</#if>
    </resultMap>
    
    <sql id="columns">
		id,<#list mainTable.columns as col>${col.name},</#list><#if mainTable.sub>${mainTable.foreignKey},</#if>comm_createBy,comm_createDate,comm_updateBy,comm_updateDate,comm_mark_for_delete
	</sql>
	
	<sql id="dynamicWhere">
		<where>
			<#list mainTable.columns as col>
			<if test="@Ognl@isNotEmpty(${col.colName})"> AND ${col.name}<#if col.type=="String"> like <#else> = </#if><#noparse>#{</#noparse>${col.colName}<#noparse>}</#noparse> </if>
			</#list>
			<#if mainTable.sub>
			<if test="@Ognl@isNotEmpty(${mainTable.parentClassName?uncap_first}Id)"> AND ${mainTable.foreignKey} =<#noparse>#{</#noparse>${mainTable.parentClassName?uncap_first}Id<#noparse>}</#noparse> </if>
			</#if>
		</where>
	</sql>
       
    <insert id="save" parameterType="${mainTable.variables.package}.model.${mainTable.variables.class}">
		INSERT INTO ${mainTable.tableName}
		(<include refid="columns"/>)
		VALUES(<#noparse>
			#{id},</#noparse>
		<#list mainTable.columns as col>
			<#noparse>#{</#noparse>${col.colName}<#noparse>},</#noparse>
		</#list>
		<#if mainTable.sub>
			<#noparse>#{</#noparse>${mainTable.parentClassName?uncap_first}Id<#noparse>},</#noparse>
		</#if><#noparse>
			#{createBy},
			#{createDate},
			#{updateBy},
			#{updateDate},
			#{isDeleted}</#noparse>
		)
	</insert>
	
	<delete id="delById" parameterType="java.lang.Long">
		DELETE FROM ${mainTable.tableName} 
		WHERE <#noparse>
		id=#{id}</#noparse>
	</delete>
	
	<update id="update" parameterType="${mainTable.variables.package}.model.${mainTable.variables.class}">
		UPDATE ${mainTable.tableName} SET
		<#list mainTable.columns as col>
		${col.name}=<#noparse>#{</#noparse>${col.colName}<#noparse>},</#noparse>
		</#list><#noparse>
		comm_updateBy=#{updateBy},
		comm_updateDate=#{updateDate}
		WHERE
		id=#{id}</#noparse>
	</update>
	
	<select id="getById" parameterType="java.lang.Long" resultMap="${mainTable.variables.class?uncap_first}ResultMap">
		SELECT <include refid="columns"/>
		FROM ${mainTable.tableName}
		WHERE <#noparse>
		id=#{id}</#noparse>
	</select>
	
	<select id="getAll" resultMap="${mainTable.variables.class?uncap_first}ResultMap">
		SELECT 
		<include refid="columns"/>
		FROM ${mainTable.tableName}  
		<include refid="dynamicWhere" />
		<if test="@Ognl@isNotEmpty(orderField)">
		order by <#noparse>${orderField} ${orderDirection}</#noparse>
		</if>
		<if test="@Ognl@isEmpty(orderField)">
		order by comm_updateDate desc
		</if>
	</select>
	<#if mainTable.sub>
	<select id="getBy${mainTable.parentClassName}Id" parameterType="java.lang.Long" resultMap="${mainTable.variables.class?uncap_first}ResultMap">
  		SELECT 
  		<include refid="columns"/>
  		FROM ${mainTable.tableName} 
  		WHERE 
  		${mainTable.foreignKey}=<#noparse>#{</#noparse>${mainTable.parentClassName?uncap_first}Id<#noparse>}</#noparse>
  	</select>
	</#if>
</mapper>