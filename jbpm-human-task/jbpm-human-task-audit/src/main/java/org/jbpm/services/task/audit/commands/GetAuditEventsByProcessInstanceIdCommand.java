/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.jbpm.services.task.audit.commands;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.jbpm.services.task.commands.TaskCommand;
import org.jbpm.services.task.utils.ClassUtil;
import org.kie.internal.command.Context;
import org.kie.internal.query.QueryFilter;
import org.kie.internal.task.api.TaskContext;
import org.kie.internal.task.api.TaskPersistenceContext;
import org.kie.internal.task.api.model.TaskEvent;

@XmlRootElement(name="get-task-audit-events-by-processinstanceid-command")
@XmlAccessorType(XmlAccessType.NONE)
public class GetAuditEventsByProcessInstanceIdCommand extends TaskCommand<List<TaskEvent>> {

	private static final long serialVersionUID = -7929370526623674312L;
        private QueryFilter filter;
        private long processInstanceId;
        
	public GetAuditEventsByProcessInstanceIdCommand() {
            this.filter = new QueryFilter(0,0);
	}
	
	public GetAuditEventsByProcessInstanceIdCommand(long processInstanceId, QueryFilter filter) {
		this.processInstanceId = processInstanceId;
                this.filter = filter;
	}
       
	@Override
	public List<TaskEvent> execute(Context context) {
		TaskPersistenceContext persistenceContext = ((TaskContext) context).getPersistenceContext();
		return persistenceContext.queryWithParametersInTransaction("getAllTasksEventsByProcessInstanceId", 
		            persistenceContext.addParametersToMap("processInstanceId", processInstanceId, "firstResult", filter.getOffset(), 
                                    "maxResults", filter.getCount()),
		            ClassUtil.<List<TaskEvent>>castClass(List.class));
		
	}

}
