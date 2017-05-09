package app.exercise.RBT.iterator;

class Iterator<RedBlackTree> implements Iterator<E> {
	protected Boolean      counter = false;        // Sets true after calling next() first time..
	protected RedBlackTree next;
	public Iterator<RedBlackTree>(RedBlackTree c)
	{
		this.next = c;
		if (next == null) { return; }
	}
	public Boolean hasNext()
	{
		return next != null;
	}

	public void remove()
	{
		if (next.childNull) //sollten beide kinder null sein
		{
			if (next.getParent().getRight() == next)
			{
				next.getParent().setRight(null);
			}
			else
			{
				next.getParent().setLeft(null);
			}
		}
		else if ((next.getRight() == null&& next.getLeft() != null))// ist das rechte Kind null und das linke nicht wird next entfert und das linke kind als ersatz benutzt.
		{
			if (!next.isBlack()) // sollte next rot sein können wir ohne repair entfernen.
			{
				if (next.getParent().getRight() == next)
				{
					next.getParent().setRight(next.getLeft());
				}
				else
				{
					next.getParent().setLeft(next.getLeft());
				}
			}
			else // falls next schwarz ist wird repariert.
			{
				if (next.getParent().getRight() == next)
				{
					RedBlackTree parent = next.getParent();
					parent.setRight(next.getLeft());
					parent.getRight().repair();
				}
				else
				{
					RedBlackTree parent = next.getParent();
					parent.setLeft(next.getLeft());
					parent.getLeft().repair();
				}
			}
		}
		else if ((next.getRight() != null&& next.getLeft() == null)) // ist das linke Kind null und das Rechte nicht wird next entfert und das recht kind als ersatz benutzt.
		{
			if (!next.isBlack()) // sollte next rot sein können wir ohne repair entfernen.
			{
				if (next.getParent().getRight() == next)
				{
					next.getParent().setRight(next.getRight());
				}
				else
				{
					next.getParent().setLeft(next.getRight());
				}
			}
			else // falls next schwarz ist wird repariert.
			{
				if (next.getParent().getRight() == next)
				{
					RedBlackTree parent = next.getParent();
					parent.setRight(next.getRight());
					parent.getRight().repair();
				}
				else
				{
					RedBlackTree parent = next.getParent();
					parent.setLeft(next.getRight());
					parent.getLeft().repair();
				}
			}
		}
		else {

		}
	}
}
class InOrderIterator<RedBlackTree> extends Iterator<RedBlackTree>
{
	public InOrderIterator(RedBlackTree c)
	{
		super(c);
		// find first node to iterate: leftmost node.
		while (next.getLeft() != null)
		{
			next = next.getLeft();
		}
	}

	public RedBlackTree next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementExeption();                                            // If there's no further element in the tree to iterate over, it's throws an exeption
		}
		RedBlackTree r = next;

		if (next.getLeft() == null)
		{
			if (next.getRight() != null)
			{
				next = next.getRight();
				while (next.getLeft() != null) { next = next.getLeft(); }
				return r;
			}
			else
			{
				while (true)
				{
					if (next.getParent() == null)
					{
						next = null;
						return r;
					}
					if (next.getParent().getLeft() == next)
					{
						next = next.getParent();
						return r;
					}
					next = next.getParent();
				}
			}
		}
	}
}


class RevInOrderIterator<RedBlackTree> extends Iterator<RedBlackTree>
{
	public RevInOrderIterator(RedBlackTree c)
	{
		super(c);
		// find first node to iterate: leftmost node.
		while (next.getRight() != null)
		{
			next = next.getRight();
		}
	}

	public RedBlackTree next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementExeption();                                            // If there's no further element in the tree to iterate over, it's throws an exeption
		}
		RedBlackTree r = next;

		if (next.getRight() == null)
		{
			if (next.getLeft() != null)
			{
				next = next.getLeft();
				while (next.getRight() != null) { next = next.getRight(); }
				return r;
			}
			else
			{
				while (true)
				{
					if (next.getParent() == null)
					{
						next = null;
						return r;
					}
					if (next.getParent().getRight() == next)
					{
						next = next.getParent();
						return r;
					}
					next = next.getParent();
				}
			}
		}
	}
}
