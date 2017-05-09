package app.exercise.RBT.tree;

import java.util.ArrayList;
import java.util.Collections;
import app.exercise.RBT.interator;

class RedBlackTree<E> extends AbstractCollection<E> {
	@Override
	public Boolean add(E e)
	{
		RedBlackTree root = this;

		while (root.getParent() != null) { root = root.getParent(); }
		while (!added && !root.getElement().equals(e))
		{
			if ((root.getElement().compareTo(e) < 0) && (root.getLeft() != null)) { root = root.getLeft(); }
			else if ((root.getElement().compareTo(e) > 0) && (root.getRight() != null)) { root = root.getRight(); }
			else
			{
				if (root.getElement.compareTo(e) < 0)
				{
					root.setLeft(new RedBlackTree(root, e));

				}
				if (root.getElement().compareTo(e) > 0)
				{
					root.setRight(new RedBlackTree(root, e));
				}
			}
		}
	}

	protected void repair()
	{
		if (!this.getParent().isBlack())
		{
			this.getParent().setFarbe(false);
			depth++;
			return;
		}
		else if (!this.getParent().isBlack() && !this.getUncle().isBlack())
		{
			this.getParent().setFarbe(false);
			this.getUncle().setFarbe(false);
			this.getParent().getParent().setFarbe(true);
			this.getParent().getParent().repair();
			return;
		}
		else if (!this.getParent().isBlack() && this.getUncle().isBlack())
		{
			if ((this.getParent().getParent().getLeft() == this.getParent()) && (this == this.getParent().getRight()))
			{
				this.getParent().rotate(true);
				this.getParent().repair();
				return;
			}
			else if ((this.getParent().getParent().getRight() == this.getParent()) && (this == this.getParent().getLeft()))
			{
				this.getParent().rotate(false);
				this.getParent().repair();
				return;
			}
			else if ((this.getParent().getParent().getLeft() == this.getParent()) && (this == this.getParent().getLeft()))
			{
				Boolean color_swap = this.getParent().getParent().isBlack;
				this.getParent().getParent().setFarbe(this.getParent().isBlack);
				this.getParent().setFarbe(color_swap);
				this.getParent().getParent().rotate(false);
				return;
			}
			else if ((this.getParent().getParent().getRight() == this.getParent()) && (this == this.getParent().getRight()))
			{
				Boolean color_swap = this.getParent().getParent().isBlack;
				this.getParent().getParent().setFarbe(this.getParent().isBlack);
				this.getParent().setFarbe(color_swap);
				this.getParent().getParent().rotate(true);
				return;
			}
		}
	}

	protected void rotation(Boolean left)
	{
		RedBlackTree X = this, Y, swap;

		if (left & this.getRight() != null)
		{
			Y    = this.getRight();
			swap = X.getParent();
			X.setRight(Y.getLeft());
			Y.setParent(swap);
		}
		if (!left & this.getLeft() != null)
		{
			Y    = this.getLeft();
			swap = X.getParent();
			X.setLeft(Y.getRight());
			Y.setParent(swap);
		}
		else{ throw new RuntimeExeption("Not Rotatable! " + X.toString()); }
	}

	/**
	 * Helper
	 */
	protected RedBlackTree getUncle()
	{
		try{
			RedBlackTree gp = this.getParent().getParent();
		}
		catch (NullPointerExeption e) {
			throw e;
		};
		if (gp.getLeft() == this.getParent())
		{
			return gp.getRight();
		}
		else
		{
			return gp.getLeft();
		}
	}

	@Override
	public int size()
	{
		int             size;
		RedBlackTree    shit = this;
		InOrderIterator iter = new InOrderIterator(this);

		while (iter.hasNext())                                     // counting through all of the nodes
		{
			size++;
			shit = iter.next();
		}

		return size;
	}

	public int depth()
	{
		return this.depth;
	}

	/*
	 * Getter Section
	 * @return RedBlackTree
	 */
	public RedBlackTree getLeft()
	{
		return this.left;
	}

	public RedBlackTree getRight()
	{
		return this.right;
	}

	public RedBlackTree getParent()
	{
		return this.parent;
	}

	public String getFarbe()
	{
		return this.farbe ? "Red" : "Black";
	}

	public E getElement()
	{
		return this.wert;
	}

	public Boolean isBlack()
	{
		return !this.farbe;
	}

	public Boolean childNull()
	{
		return this.getLeft == null&& this.getRight() == null;
	}

	/**
	 * Setter Section
	 */
	public void setLeft(RedBlackTree child)
	{
		this.left = child;
	}

	public void setRight(RedBlackTree child)
	{
		this.right = child;
	}

	public void setParent(RedBlackTree parent)
	{
		this.parent = parent;
	}

	public void setFarbe(Boolean farbe)
	{
		this.farbe = farbe;
	}

	/**
	 * Default empty RedBlackTree constructor
	 */
	public RedBlackTree()
	{
		this.parent = null;
		this.wert   = null;
		depth++;
	}

	/**
	 * Default RedBlackTree constructor
	 */
	public RedBlackTree(RedBlackTree parent, E val)
	{
		this.parent = parent;
		this.wert   = val;
		depth++;
	}

	public RedBlackTree(RedBlackTree parent, E val, Boolean f)
	{
		this.parent = parent;
		this.wert   = val;
		this.farbe  = f;
		depth++;
	}

	protected RedBlackTree parent;
	protected RedBlackTree left;
	protected RedBlackTree right;
	protected Boolean      farbe = false;                                             // ein Node ist per Default schwarz(false), sollte diese Variable == true sein ist die Farbe rot.
	protected E            wert;
	protected static int   depth;
}
