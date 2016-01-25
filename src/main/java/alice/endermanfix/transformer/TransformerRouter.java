package alice.endermanfix.transformer;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import alice.endermanfix.EndermanFix;
import net.minecraft.launchwrapper.IClassTransformer;

public final class TransformerRouter implements IClassTransformer
{
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		boolean deobfuscated = name.equals(transformedName);
		if(transformedName.equals("net.minecraft.entity.monster.EntityEnderman"))
		{
			EndermanFix.LOG.info("EntityEndermanを修正…");
			return TEntityEnderman.transform(deobfuscated, basicClass);
		}
		else if(transformedName.equals("net.minecraft.block.BlockEndPortal"))
		{
			EndermanFix.LOG.info("BlockEndPortalを修正…");
			return TBlockEndPortal.transform(deobfuscated, basicClass);
		}
		return basicClass;
	}

	public static MethodNode getMethodNode(ClassNode classNode, String methodName, String signature)
	{
		StringBuilder msg = new StringBuilder(256);
		msg.append("このメソッドを検索:").append(methodName).append('[').append(signature).append(']');
		EndermanFix.LOG.info(msg);
		for(MethodNode n : classNode.methods)
		{
			msg = new StringBuilder(256);
			msg.append("…").append(n.name).append('[').append(n.desc).append(']');
			EndermanFix.LOG.info(msg);
			if((n.name.equals(methodName)) && (n.desc.equals(signature)))
			{
				EndermanFix.LOG.info("メソッドが見つかりました。");
				return n;
			}
		}
		EndermanFix.LOG.info("メソッドは見つかりませんでした。");
		return null;
	}
}
