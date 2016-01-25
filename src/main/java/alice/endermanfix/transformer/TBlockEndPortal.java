package alice.endermanfix.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import alice.endermanfix.EndermanFix;

public final class TBlockEndPortal
{
	public static byte[] transform(boolean deobfuscated, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		MethodNode onBlockAdded;
		if(deobfuscated)
		{
			onBlockAdded = TransformerRouter.getMethodNode(classNode, "onBlockAdded", "(Lnet/minecraft/world/World;III)V");
		}
		else
		{
			onBlockAdded = TransformerRouter.getMethodNode(classNode, "func_149726_b", "(Lnet/minecraft/world/World;III)V");
			if(onBlockAdded == null)
			{
				onBlockAdded = TransformerRouter.getMethodNode(classNode, "b", "(Lahb;III)V");
			}
		}
		if(onBlockAdded == null)
		{
			EndermanFix.LOG.error("onBlockAddedメソッドを発見できませんでした。");
			return basicClass;
		}

		classNode.methods.remove(onBlockAdded);
		EndermanFix.LOG.info("onBlockAddedメソッドを削除しました。");

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		classNode.accept(classWriter);

		return classWriter.toByteArray();
	}
}
