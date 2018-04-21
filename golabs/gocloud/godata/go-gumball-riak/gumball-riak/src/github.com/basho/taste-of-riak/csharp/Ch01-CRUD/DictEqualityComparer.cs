namespace Ch01_CRUD
{
    using System.Collections.Generic;
    using System.Linq;

    public class DictEqualityComparer<TKey, TValue> : IEqualityComparer<IDictionary<TKey, TValue>>
    {
        public bool Equals(IDictionary<TKey, TValue> x, IDictionary<TKey, TValue> y)
        {
            if (ReferenceEquals(x, null) && ReferenceEquals(y, null))
            {
                return true;
            }

            if ((ReferenceEquals(x, null) && !ReferenceEquals(y, null)) ||
                (ReferenceEquals(y, null) && !ReferenceEquals(x, null)))
            {
                return false;
            }

            return x.Count == y.Count && !x.Except(y).Any();
        }

        public int GetHashCode(IDictionary<TKey, TValue> obj)
        {
            return obj.GetHashCode();
        }
    }
}
