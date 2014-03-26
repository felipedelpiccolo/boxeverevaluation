package jobs;

import models.UserDao;
import models.UserDto;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

import play.jobs.Job;
import play.jobs.OnApplicationStart;


@OnApplicationStart
public class SetUpAstyanax extends Job{

	public static Keyspace keyspace;
	
	@Override
	public void doJob() throws ConnectionException {
		AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
	    .forCluster("localhost")
	    .forKeyspace("astyanaxPOC")
	    .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()      
	        .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
	    )
	    .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
	        .setPort(9160)
	        .setMaxConnsPerHost(1)
	        .setSeeds("127.0.0.1:9160")
	    )
	    .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
	    .buildKeyspace(ThriftFamilyFactory.getInstance());

		context.start();
		this.keyspace = context.getEntity();

		
	}
}
