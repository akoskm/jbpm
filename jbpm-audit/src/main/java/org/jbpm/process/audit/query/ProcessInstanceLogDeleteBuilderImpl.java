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

package org.jbpm.process.audit.query;

import static org.kie.internal.query.QueryParameterIdentifiers.END_DATE_LIST;
import static org.kie.internal.query.QueryParameterIdentifiers.EXTERNAL_ID_LIST;
import static org.kie.internal.query.QueryParameterIdentifiers.IDENTITY_LIST;
import static org.kie.internal.query.QueryParameterIdentifiers.OUTCOME_LIST;
import static org.kie.internal.query.QueryParameterIdentifiers.PROCESS_INSTANCE_STATUS_LIST;
import static org.kie.internal.query.QueryParameterIdentifiers.PROCESS_NAME_LIST;
import static org.kie.internal.query.QueryParameterIdentifiers.PROCESS_VERSION_LIST;
import static org.kie.internal.query.QueryParameterIdentifiers.START_DATE_LIST;

import java.util.Date;

import org.jbpm.process.audit.JPAAuditLogService;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.kie.api.runtime.CommandExecutor;
import org.kie.internal.query.ParametrizedUpdate;
import org.kie.internal.query.data.QueryData;
import org.kie.internal.runtime.manager.audit.query.ProcessInstanceLogDeleteBuilder;

public class ProcessInstanceLogDeleteBuilderImpl extends
		AbstractAuditDeleteBuilderImpl<ProcessInstanceLogDeleteBuilder> implements ProcessInstanceLogDeleteBuilder {

	public ProcessInstanceLogDeleteBuilderImpl(JPAAuditLogService jpaService) {
		super(jpaService);
		intersect();
	}

	public ProcessInstanceLogDeleteBuilderImpl(CommandExecutor cmdExecutor) {
		super(cmdExecutor);
		intersect();
	}

	@Override
	public ProcessInstanceLogDeleteBuilder processInstanceId(long... processInstanceId) {
		if (checkIfNotNull(processInstanceId)) {
			return this;
		}
		return super.processInstanceId(processInstanceId);
	}

	@Override
	public ProcessInstanceLogDeleteBuilder processId(String... processId) {
		if (checkIfNotNull(processId)) {
			return this;
		}
		return super.processId(processId);
	}

	@Override
	public ProcessInstanceLogDeleteBuilder date(Date... date) {
		if (checkIfNotNull(date)) {
			return this;
		}
		return super.date(ensureDateNotTimestamp(date));
	}

	@Override
	public ProcessInstanceLogDeleteBuilder dateRangeStart(Date rangeStart) {
		if (checkIfNotNull(rangeStart)) {
			return this;
		}
		return super.dateRangeStart(ensureDateNotTimestamp(rangeStart)[0]);
	}

	@Override
	public ProcessInstanceLogDeleteBuilder dateRangeEnd(Date rangeStart) {
		if (checkIfNotNull(rangeStart)) {
			return this;
		}
		return super.dateRangeEnd(ensureDateNotTimestamp(rangeStart)[0]);
	}

	@Override
	public ProcessInstanceLogDeleteBuilder status(int... status) {
		if (checkIfNotNull(status)) {
			return this;
		}
		addIntParameter(PROCESS_INSTANCE_STATUS_LIST, "status", status);
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder outcome(String... outcome) {
		if (checkIfNotNull(outcome)) {
			return this;
		}
		addObjectParameter(OUTCOME_LIST, "outcome", outcome);
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder identity(String... identity) {
		if (checkIfNotNull(identity)) {
			return this;
		}
		addObjectParameter(IDENTITY_LIST, "identity", identity);
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder processVersion(String... version) {
		if (checkIfNotNull(version)) {
			return this;
		}
		addObjectParameter(PROCESS_VERSION_LIST, "process version", version);
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder processName(String... processName) {
		if (checkIfNotNull(processName)) {
			return this;
		}
		addObjectParameter(PROCESS_NAME_LIST, "process name", processName);
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder startDate(Date... date) {
		if (checkIfNotNull(date)) {
			return this;
		}
		addObjectParameter(START_DATE_LIST, "start date", ensureDateNotTimestamp(date));
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder startDateRangeStart(Date rangeStart) {
		if (checkIfNotNull(rangeStart)) {
			return this;
		}
		addRangeParameter(START_DATE_LIST, "start date range, start", ensureDateNotTimestamp(rangeStart)[0], true );
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder startDateRangeEnd(Date rangeEnd) {
		if (checkIfNotNull(rangeEnd)) {
			return this;
		}
		addRangeParameter(START_DATE_LIST, "start date range, end", ensureDateNotTimestamp(rangeEnd)[0], false );
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder endDate(Date... date) {
		if (checkIfNotNull(date)) {
			return this;
		}
		addObjectParameter(END_DATE_LIST, "end date", ensureDateNotTimestamp(date) );
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder endDateRangeStart(Date rangeStart) {
		if (checkIfNotNull(rangeStart)) {
			return this;
		}
		addRangeParameter(END_DATE_LIST, "end date range, start", ensureDateNotTimestamp(rangeStart)[0], true);
        return this;
	}

	@Override
	public ProcessInstanceLogDeleteBuilder endDateRangeEnd(Date rangeEnd) {
		if (checkIfNotNull(rangeEnd)) {
			return this;
		}
		addRangeParameter(END_DATE_LIST, "end date range, end", ensureDateNotTimestamp(rangeEnd)[0], false);
        return this;
	}
	
	@Override
	public ProcessInstanceLogDeleteBuilder externalId(String... externalId) {
		if (checkIfNotNull(externalId)) {
			return this;
		}
		addObjectParameter(EXTERNAL_ID_LIST, "external id", externalId);
		return this;
	}

	@Override
	public ParametrizedUpdate build() {

		return new ParametrizedUpdate() {
			private QueryData queryData = new QueryData(getQueryData());
			@Override
			public int execute() {
				int result = getJpaAuditLogService().doDelete(queryData, ProcessInstanceLog.class);
				return result;
			}
		};
	}

}
