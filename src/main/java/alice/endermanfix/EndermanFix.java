package alice.endermanfix;
/* -Dfml.coreMods.load=alice.endermanfix.EndermanFix */

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"alice.endermanfix"})
public final class EndermanFix extends DummyModContainer implements IFMLLoadingPlugin
{
	public static final Logger LOG = LogManager.getLogger("endermanfix");
	public static EndermanFix INSTANCE;
	private static final String[] asmTransformers = new String[1];

	static
	{
		asmTransformers[0] = "alice.endermanfix.transformer.TransformerRouter";
	}

	public EndermanFix()
	{
		super(new ModMetadata());

		ModMetadata meta = this.getMetadata();
		meta.modId = "endermanfix";
		meta.name = "Enderman Fix";
		meta.version = "1.0";
		meta.description = "エンダーマンの挙動を修正するMODです。";
		meta.url = "http://a1lic.net";
		meta.authorList = Collections.singletonList("alice");
		meta.credits = "alice";

		if(INSTANCE == null)
		{
			INSTANCE = this;
		}
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(Map<String, String> mods, Side side)
	{
		return true;
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(INSTANCE);
		return true;
	}

	public String[] getASMTransformerClass()
	{
		return asmTransformers.clone();
	}

	public String getModContainerClass()
	{
		return EndermanFix.class.getName();
	}

	public String getSetupClass()
	{
		return null;
	}

	public void injectData(Map<String, Object> data)
	{
	}

	public String getAccessTransformerClass()
	{
		return null;
	}

}
