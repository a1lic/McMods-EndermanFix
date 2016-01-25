package alice.endermanfix.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import alice.endermanfix.EndermanFix;

public final class TEntityEnderman
{
	public static byte[] transform(boolean deobfuscated, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		MethodNode m = canDespawn(deobfuscated);
		classNode.methods.add(m);
		EndermanFix.LOG.info("canDespawnメソッドを追加しました。");

		m = setDead(deobfuscated);
		classNode.methods.add(m);
		EndermanFix.LOG.info("setDeadメソッドを追加しました。");

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		classNode.accept(classWriter);

		return classWriter.toByteArray();
	}

	private static MethodNode canDespawn(boolean deobfuscated)
	{
		MethodNode m;
		if(deobfuscated)
		{
			m = new MethodNode(Opcodes.ACC_PROTECTED, "canDespawn", "()Z", null, null);
		}
		else
		{
			m = new MethodNode(Opcodes.ACC_PROTECTED, "func_70692_ba", "()Z", null, null);
		}

		InsnList i = m.instructions;

		LabelNode hasNoBlock = new LabelNode();
		LabelNode skip = new LabelNode();

		i.add(new IntInsnNode(Opcodes.ALOAD, 0));
		i.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/monster/EntityEnderman", "func_146080_bZ", "()Lnet/minecraft/block/Block;", false));
		if(deobfuscated)
		{
			i.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/init/Blocks", "air", "Lnet/minecraft/block/Block;"));
		}
		else
		{
			i.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/init/Blocks", "field_150350_a", "Lnet/minecraft/block/Block;"));
		}
		i.add(new JumpInsnNode(Opcodes.IF_ACMPEQ, hasNoBlock));
		i.add(new InsnNode(Opcodes.ICONST_0));
		i.add(new JumpInsnNode(Opcodes.GOTO, skip));
		i.add(hasNoBlock);
		i.add(new IntInsnNode(Opcodes.ALOAD, 0));
		if(deobfuscated)
		{
			i.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/entity/monster/EntityMob", "canDespawn", "()Z", false));
		}
		else
		{
			i.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/entity/monster/EntityMob", "func_70692_ba", "()Z", false));
		}
		i.add(skip);
		i.add(new InsnNode(Opcodes.IRETURN));

		return m;
	}

	private static MethodNode setDead(boolean deobfuscated)
	{
		MethodNode m;
		if(deobfuscated)
		{
			m = new MethodNode(Opcodes.ACC_PROTECTED, "setDead", "()V", null, null);
		}
		else
		{
			m = new MethodNode(Opcodes.ACC_PROTECTED, "func_70106_y", "()V", null, null);
		}

		InsnList i = m.instructions;

		LabelNode hasBlock = new LabelNode();
		LabelNode isNotRemote = new LabelNode();

		i.add(new IntInsnNode(Opcodes.ALOAD, 0));
		if(deobfuscated)
		{
			i.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/entity/monster/EntityMob", "setDead", "()V", false));
		}
		else
		{
			i.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/entity/monster/EntityMob", "func_70106_y", "()V", false));
		}
		i.add(new IntInsnNode(Opcodes.ALOAD, 0));
		i.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/monster/EntityEnderman", "func_146080_bZ", "()Lnet/minecraft/block/Block;", false));
		i.add(new IntInsnNode(Opcodes.ASTORE, 1));
		i.add(new IntInsnNode(Opcodes.ALOAD, 1));
		if(deobfuscated)
		{
			i.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/init/Blocks", "air", "Lnet/minecraft/block/Block;"));
		}
		else
		{
			i.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/init/Blocks", "field_150350_a", "Lnet/minecraft/block/Block;"));
		}
		i.add(new JumpInsnNode(Opcodes.IF_ACMPNE, hasBlock));
		i.add(new InsnNode(Opcodes.RETURN));
		i.add(hasBlock);
		if(deobfuscated)
		{
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "worldObj", "Lnet/minecraft/world/World;"));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/World", "isRemote", "Z"));
		}
		else
		{
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "field_70170_p", "Lnet/minecraft/world/World;"));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/World", "field_72995_K", "Z"));
		}
		i.add(new JumpInsnNode(Opcodes.IFEQ, isNotRemote));
		i.add(new InsnNode(Opcodes.RETURN));
		i.add(isNotRemote);
		i.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/item/ItemStack"));
		i.add(new InsnNode(Opcodes.DUP));
		i.add(new IntInsnNode(Opcodes.ALOAD, 1));
		i.add(new InsnNode(Opcodes.ICONST_1));
		i.add(new IntInsnNode(Opcodes.ALOAD, 0));
		if(deobfuscated)
		{
			i.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/monster/EntityEnderman", "getCarryingData", "()I", false));
		}
		else
		{
			i.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/entity/monster/EntityEnderman", "func_70824_q", "()I", false));
		}
		i.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/item/ItemStack", "<init>", "(Lnet/minecraft/block/Block;II)V", false));
		i.add(new IntInsnNode(Opcodes.ASTORE, 2));
		i.add(new TypeInsnNode(Opcodes.NEW, "net/minecraft/entity/item/EntityItem"));
		i.add(new InsnNode(Opcodes.DUP));
		if(deobfuscated)
		{
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "worldObj", "Lnet/minecraft/world/World;"));
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "posX", "D"));
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "posY", "D"));
			i.add(new LdcInsnNode(0.5));
			i.add(new InsnNode(Opcodes.DADD));
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "posZ", "D"));
		}
		else
		{
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "field_70170_p", "Lnet/minecraft/world/World;"));
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "field_70165_t", "D"));
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "field_70163_u", "D"));
			i.add(new LdcInsnNode(0.5));
			i.add(new InsnNode(Opcodes.DADD));
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "field_70161_v", "D"));
		}
		i.add(new IntInsnNode(Opcodes.ALOAD, 2));
		i.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/minecraft/entity/item/EntityItem", "<init>", "(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V", false));
		i.add(new IntInsnNode(Opcodes.ASTORE, 3));
		i.add(new IntInsnNode(Opcodes.ALOAD, 0));
		if(deobfuscated)
		{
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "worldObj", "Lnet/minecraft/world/World;"));
		}
		else
		{
			i.add(new IntInsnNode(Opcodes.ALOAD, 0));
			i.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/monster/EntityEnderman", "field_70170_p", "Lnet/minecraft/world/World;"));
		}
		i.add(new IntInsnNode(Opcodes.ALOAD, 3));
		if(deobfuscated)
		{
			i.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/world/World", "spawnEntityInWorld", "(Lnet/minecraft/entity/Entity;)Z", false));
		}
		else
		{
			i.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/world/World", "func_72838_d", "(Lnet/minecraft/entity/Entity;)Z", false));
		}
		i.add(new InsnNode(Opcodes.POP));
		i.add(new InsnNode(Opcodes.RETURN));

		return m;
	}
}
